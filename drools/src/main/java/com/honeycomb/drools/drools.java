package com.honeycomb.drools;

import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.process.Process;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.Collection;

/**
 * @author maoliang
 * @version 1.0.0
 */
public class drools {

    public static void main(String[] args) {
        KieServices kieServices = KieServices.Factory.get();
        //默认自动加载 META-INF/kmodule.xml
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        //kmodule.xml 中定义的 ksession name
        KieSession kieSession = kieContainer.newKieSession("all-rules");

        Collection<KiePackage> kiePackages = kieSession.getKieBase().getKiePackages();
        Collection<Process> processes = kieSession.getKieBase().getProcesses();
        System.out.println("honeycomb : " + kiePackages + "   " + processes);

        Person p1 = new Person();
        p1.setName("ins_art_zhiliang");
        p1.setAge(40);
        Car car = new Car();
        car.setPerson(p1);

        kieSession.insert(car);

        int count = kieSession.fireAllRules();

        System.out.println(count);
        System.out.println(car.getDiscount());

        kieSession.dispose();
    }
}
