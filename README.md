# osgi-ninja-maven-plugin
Update OSGI dependencies to local deployment repository

This plugin uses [osgi-ninja-core](https://github.com/rritoch/osgi-ninja-core)

## Usage

After creating your dependency file (see:[osgi-ninja-core](https://github.com/rritoch/osgi-ninja-core))  add the following plugin to your build profile

```
			<plugin>
				<groupId>com.vnetpublishing.java</groupId>
				<artifactId>osgi-ninja-maven-plugin</artifactId>
				<version>0.0.1-SNAPSHOT</version>
				<configuration>
				  <!-- Optional, defaults to: bundle -->
					<bundlePath>bundle</bundlePath>
					<!-- Optional, defaults to: osgideps.xml -->
					<source>osgideps.xml</source>
				</configuration>
			</plugin>
```

Execute the plugin target

```
mvn osgi-ninja:update
```

## Status

This plugin is now functional and in beta testing

