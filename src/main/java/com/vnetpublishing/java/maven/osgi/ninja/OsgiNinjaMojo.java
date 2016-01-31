package com.vnetpublishing.java.maven.osgi.ninja;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import javax.xml.bind.JAXBException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.vnetpublishing.java.osgi.tools.OsgiUserBundleRespository;

@Mojo( name = "update")
public class OsgiNinjaMojo extends AbstractMojo {
	
    @Parameter( defaultValue = "bundle" )
    private File bundlePath;
	
    @Parameter( defaultValue = "osgideps.xml" )
    private File source;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (!source.exists()) {
			throw new MojoExecutionException("OSGI Dependencies source not found");
		}
		
		if (!bundlePath.exists()) {
			if (!bundlePath.mkdirs()) {
				throw new MojoExecutionException("Unable to create bundle path");
			}
		}
		
		if (!bundlePath.isDirectory()) {
			throw new MojoExecutionException("Bundle path is not a directory");
		}
		
		//System.setProperty( "java.protocol.handler.pkgs", "org.ops4j.pax.url" );
		
		URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
				public URLStreamHandler createURLStreamHandler(String protocol) {
					if ("wrap".equals(protocol)) {
						return new org.ops4j.pax.url.wrap.Handler();
					}
					if ("mvn".equals(protocol)) {
						return new org.ops4j.pax.url.mvn.Handler();
					}
					return null;
				}
		});
		
		
		OsgiUserBundleRespository repository = new OsgiUserBundleRespository(bundlePath);
		URL srcUrl;
		try {
			srcUrl = source.toURI().toURL();
		} catch (MalformedURLException e) {
			throw new MojoExecutionException("Invalid source",e);
		}
		
		try {
			repository.updateRepository(srcUrl, false);
		} catch (JAXBException e) {
			throw new MojoExecutionException("Update failed",e);
		} catch (IOException e) {
			throw new MojoExecutionException("Update failed",e);
		} catch (URISyntaxException e) {
			throw new MojoExecutionException("Update failed",e);
		}
		
	}
}
