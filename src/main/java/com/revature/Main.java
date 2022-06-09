package com.revature;

import java.io.File;

import com.revature.controller.RequestMapper;

import io.javalin.Javalin;
import io.javalin.plugin.metrics.MicrometerPlugin;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.DiskSpaceMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class Main {

	public static void main(String[] args) {
		
		PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
		
		registry.config().commonTags("application","Project-1");
		
		new ClassLoaderMetrics().bindTo(registry);
		new JvmMemoryMetrics().bindTo(registry);
		new JvmGcMetrics().bindTo(registry);
		new JvmThreadMetrics().bindTo(registry);
		new UptimeMetrics().bindTo(registry);
		new ProcessorMetrics().bindTo(registry);
		new DiskSpaceMetrics(new File(System.getProperty("user.dir"))).bindTo(registry);
		
		Javalin app = Javalin.create(config -> {
			config.registerPlugin(new MicrometerPlugin(registry));
		}).start(2237);
		
		RequestMapper requestMapper = new RequestMapper();
		
		requestMapper.configureRoutes(app);
		
	}

}