package com.yicj.study.importbeandefinitionregistrar;

import feign.Feign;
import feign.Target;
import org.springframework.cloud.openfeign.FeignContext;

interface Targeter {

	<T> T target(FeignClientFactoryBean factory, Feign.Builder feign,
				 FeignContext context, Target.HardCodedTarget<T> target);

}