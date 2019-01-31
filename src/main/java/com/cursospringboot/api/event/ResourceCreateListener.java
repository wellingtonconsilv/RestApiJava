package com.cursospringboot.api.event;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class ResourceCreateListener implements ApplicationListener<ResourceCreateEvent>{

		@Override
		public void onApplicationEvent(ResourceCreateEvent event) {
			HttpServletResponse response = event.getResponse();
			Long id = event.getId();
			
			setHeaderLocation(response, id);
		}

		private void setHeaderLocation(HttpServletResponse response, Long id) {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
					.buildAndExpand(id).toUri();
			response.setHeader("Location", uri.toASCIIString());
		}
}
