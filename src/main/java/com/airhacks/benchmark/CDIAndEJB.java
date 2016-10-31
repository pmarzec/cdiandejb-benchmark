/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.benchmark;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

/**
 *
 * @author pmarzec
 */
@BenchmarkMode(Mode.Throughput)
public class CDIAndEJB {

    @State(Scope.Thread)
    public static class BenchmarkContext {
        
        @Param({"cdi", "ejb"})
        private String component;
        
        WebTarget tut;
        
        @Setup
        public void initClient() {
            Client client = ClientBuilder.newClient();
            this.tut = client.target("http://localhost:8080/cdiandejb/resources/{component}");
        }
    }
    
    @Benchmark
    public void executeBenchmark(BenchmarkContext ctx) {
        ctx.tut.resolveTemplate("component", ctx.component).request().get(String.class);
    }
}

