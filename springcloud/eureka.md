##Eureka
### 服务提供者
#### 服务注册
"服务提供者"在启动的时候会通过发送REST请求的方式将[自己注册到Eureka Server](`com.netflix.discovery.DiscoveryClient.register`)上,同时带上了自身的一些元数据信息。Eureka Server接收到这个REST请求之后，将元数据信息存储在一个双层结构的[Map](ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> ，Lease包含了该实例实例在注册中心变化的状态值，例如当前实例状态、最后更新时间时间等的)中，其中第一层的key是服务名，第二层的key是具体服务的实例名（每个实例都有一个唯一标识ID）。

在注册时，需要确认下`eureka.client.register-with-eureka=true`参数是否正确，该值默认为true。若设置为false将不会启动注册操作。

#### 服务同步

由于服务注册中心之间因互相注册为服务，[当服务提供者发送注册请求到一个服务注册中心时，它会将该请求转发给集群中相连的其他注册中心，从而实现注册中心之间的服务同步](com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl#replicateToPeers)。通过服务同步，两个服务提供者的服务信息就可以通过这两台服务注册中心中的任意一台获取到。

#### 服务续约

在注册完服务之后，服务提供者会维护一个心跳用来持续告诉Eureka Server：“我还活着”，以防止Eureka Server的“剔除任务”将该服务实例从服务列表中排除出去。

配置属性：

```properties
#定义服务续约任务调用间隔时间
eureka.instance.lease-renewal-interval-in-seconds=30
#定义服务失效的时间，90秒没有收到任何请求，则任务服务挂了
eureka.instance.lease-expiration-duration-in-seconds=90
```

>  问题：
>
> 1. 注册时如何知道是服务提供者还是服务消费者、注册中心？
>
>    答：不区分服务提供者和服务消费者，只有服务注册中心和服务注册者。
>
>    注册中心是通过获取所有服务注册者上的服务注册地址来创建的。
>
>    步骤如下：
>
>    [1. 获取服务注册中心地址的通过服务注册者](com.netflix.eureka.cluster.PeerEurekaNodes#resolvePeerUrls)
>
>    [2. 更新所有注册中心的信息](com.netflix.eureka.cluster.PeerEurekaNodes#updatePeerEurekaNodes)
>
>    > 1. 注册中心的管理类：`com.netflix.eureka.cluster.PeerEurekaNodes`，[在启动之后会有定时任务去更新注册中心的信息](com.netflix.eureka.cluster.PeerEurekaNodes#start)。
>    >
>    > 2. 因为每个服务注册者都会在注册服务后同步到其他服务注册中心，所以服务注册中心通过自己实例获取的服务注册地址几乎是实时的（定时拉取）。
>
> 2. 如果服务挂了，注册中心是通过什么方式告诉其他消费者和注册中心？
>
>    答：
>
>    1. 主动下线通过服务提供REST请求通知到服务注册中心，服务注册中心收到请求之后，同步到其他服务注册中心。
>
>    2. 被动下线（内存不足等），服务注册中心定时获取心跳失败，下线该服务，同步到其他服务注册中心。

### 服务消费者

#### 获取服务

当我们启动服务消费者的时候，它会发送一个REST请求给服务注册中心，来获取上面注册的服务清单。为了性能考虑，Eureka Server会维护一份只读的服务清单来返回给客户端，同时该缓存清单每隔30秒更新一次。

配置属性：

```properties
#是否刷新注册列表
eureka.client.fetch-registry=true
#缓存清单的更新时间
eureka.client.registry-fetch-interval-seconds=30
```

#### 服务调用

服务消费者在获取服务清单后，通过服务名可以获得具体提供的实例名和该实例的元数据信息。因为有这些服务实例的详细信息，所以客户端可以根据自己的需要决定具体调用哪个实例，在Rabbin中会默认采用轮询的方式进行调用，从而实现客户端的负载均衡。

对于访问实例的选择，Eureka中有Region和Zone的概念，一个Region中可以包含多个Zone，每个服务客户端需要被注册到一个Zone中，所以每个客户端对应一个Region和一个Zone。在进行服务调用的时候，优先访问同处一个Zone中的服务提供方，若访问不到，就访问其他的Zone。

配置属性：

````yaml
eureka:
  instance:
    prefer-ip-address: true
    status-page-url-path: /actuator/info
    health-check-url-path: /actuator/health
    metadata-map:
			#当前实例所属zone
      zone: zone-1
  client:
    register-with-eureka: true
    fetch-registry: true
    #同zone的时候优先
    prefer-same-zone-eureka: true
    #地区:通过地区找可用的zone
    region: beijing
    #可用空间：通过可用空间找注册中心地址
    availability-zones:
      beijing: zone-1,zone-2
    #注册中心地址
    service-url:
      zone-1: http://localhost:30000/eureka/
      zone-2: http://localhost:30001/eureka/
````

**注册中心选择逻辑**：

1. 如果prefer-same-zone-eureka为false，按照service-url下的 list取第一个注册中心来注册，并和其维持心跳检测。不会再向list内的其它的注册中心注册和维持心跳。只有在第一个注册失败的情况下，才会依次向其它的注册中心注册，总共重试3次，如果3个service-url都没有注册成功，则注册失败。每隔一个心跳时间，会再次尝试。
2. 如果prefer-same-zone-eureka为true，先通过region取availability-zones内的第一个zone，然后通过这个zone取service-url下的list，并向list内的第一个注册中心进行注册和维持心跳，不会再向list内的其它的注册中心注册和维持心跳。只有在第一个注册失败的情况下，才会依次向其它的注册中心注册，总共重试3次，如果3个service-url都没有注册成功，则注册失败。每隔一个心跳时间，会再次尝试。

**所以说，为了保证服务注册到同一个zone的注册中心，一定要注意availability-zones的顺序，必须把同一zone写在前面**

> 问题：
>
> 1. 会去其他Region中获取服务实例吗？
>
>    答：不会，代码：com.netflix.discovery.endpoint.EndpointUtils#getServiceUrlsMapFromConfig

#### 服务下线

在系统运行过程中必然会面临关闭或重启服务的某个实例的情况，在服务关闭期间，我们自然不希望客户端继续调用关闭了的实例。所以在客户端程序中，当服务实例进行正常关闭的操作时，它会触发一个服务下线的REST请求给Eureka Server，告诉服务注册中心：“我要下线了”。服务端在接受到请求之后，将该服务状态置为下线（DOWN），并把该下线时间传播出去。

### 服务注册中心

#### 失效剔除

有些时候，我们的服务实例并不一定会正常下线，可能由于内存溢出、网络故障的等原因使得服务不能正常工作，而服务注册中心未收到“服务下线”的请求。为了从服务列表中将这些无法提供服务的实例剔除，Eureka Server在启动的时候会创建一个定时任务，默认每隔一段时间（默认为60秒）将当前清单中超时（默认90秒）没有续约的服务剔除。

#### 自我保护

服务注册到Eureka Server 之后，会维护一个心跳连接，告诉Eureka Server 自己还活着。Eureka Server在运行期间，会统计心跳失败的比例，在15分钟之内是否低于85%（85%的客户端没有心跳），如果出现低于的情况（通常是由于网络不稳定导致），Eureka Server 会将当前的实例注册信息保护起来，让这些实例不会过期，尽可能保护这些注册信息。但是，在这段保护期间内，实例若出现问题，那么客户端很容难道时间已经不存在的服务实例，会出现调用失败的情况，所以客户端必须要有容错机制，比如可以使用请求重试、断路其的机制。

> 由于本地调试很容易触发注册中心的保护机制，这会使得注册中心维护的服务实例不那么准确。所以我们在本地开发的时候，可以使用`eureka.server.enable-self-preservation=false`参数来关闭保护机制，以确保注册中心可以将不可用的实例正确剔除。

配置属性：

```properties
#自我保护模式，当出现网络分区故障、频繁的开启关闭客户端、eureka在短时间内丢失过多客户端时，会进入自我保护模式，即一个服务长时间没有发送心跳，eureka也不会将其删除，默认为true
eureka.server.enable-self-preservation=true

#eureka server清理无效节点的时间间隔，默认60000毫秒，即60秒   
eureka.server.eviction-interval-timer-in-ms=60000

#阈值更新的时间间隔，单位为毫秒，默认为15 * 60 * 1000  
eureka.server.renewal-threshold-update-interval-ms=15 * 60 * 1000

#阈值因子，默认是0.85，如果阈值比最小值大，则自我保护模式开启  
eureka.server.renewal-percent-threshold=0.85
  
#清理任务程序被唤醒的时间间隔，清理过期的增量信息，单位为毫秒，默认为30 * 1000
eureka.server.delta-retention-timer-interval-in-ms=30000
```

> 问题：
>
> 1. 当保护机制打开之后，什么时候关闭？
>
>    答：当心跳检查恢复正常之后。
>
> `Renews threshold`：**Eureka Server 期望每分钟收到客户端实例续约的总数**。
> `Renews (last min)`：**Eureka Server 最后 1 分钟收到客户端实例续约的总数**（因为每个服务默认续租时间为30秒）。
>
> 当`Renews (last min)` < `Renews threshold`时开启保护机制。
>
> `Renews threshold` = 服务总数  *  2  * 阀值因子（默认0.85）
>
> 参考文档：https://www.cnblogs.com/xishuai/p/spring-cloud-eureka-safe.html