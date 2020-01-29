package br.com.wises.services;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.wises.services.CORSFilter.class);
        resources.add(br.com.wises.services.OrganizacaoService.class);
        resources.add(br.com.wises.services.SalaService.class);
        resources.add(br.com.wises.services.UsuarioService.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    }
    
}
