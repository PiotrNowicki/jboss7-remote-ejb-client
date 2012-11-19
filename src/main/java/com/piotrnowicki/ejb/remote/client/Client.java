package com.piotrnowicki.ejb.remote.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.google.common.base.Strings;

public class Client {

	public static void main(String[] args) throws NamingException {
		new Client().run();
	}

	public void run() throws NamingException {
		Properties jndiProperties = new Properties();

		jndiProperties.put(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");
		InitialContext context = new InitialContext(jndiProperties);

		Object o = context.lookup(getJNDICoordinates("appName", "moduleName", "beanName",
				"interfaceName"));
		System.out.println(o);
	}

	/**
	 * Constructs the JNDI coordinates for the given EJB.
	 * <p>
	 * This method uses the EJB Client API coordinates (starting with
	 * <code>ejb:</code>) and doesn't do any lookup - it merely provides you
	 * with the JNDI coordinates.
	 * </p>
	 * <p>
	 * The format of the EJB Client API is as follows: <br/>
	 * <code>ejb:[appName]/[moduleName]/[beanName][!interfaceName][?stateful]</code>
	 * </p>
	 * 
	 * @param appName
	 *            application name
	 * @param moduleName
	 *            module name
	 * @param beanName
	 *            bean name
	 * @param interfaceName
	 *            interface name
	 * @param isStateful
	 *            is it the SFSB?
	 * 
	 * @return JNDI coordinates
	 */
	String getJNDICoordinates(String appName, String moduleName,
			String beanName, String interfaceName, boolean isStateful) {
		String result = "ejb:" + appName + "/" + moduleName + "/" + beanName;

		if (!Strings.isNullOrEmpty(interfaceName)) {
			result += "!" + interfaceName;
		}

		if (isStateful) {
			result += "?stateful";
		}

		return result;
	}

	String getJNDICoordinates(String appName, String moduleName,
			String beanName, String interfaceName) {
		return getJNDICoordinates(appName, moduleName, beanName, interfaceName,
				false);
	}

	String getJNDICoordinates(String appName, String moduleName, String beanName) {
		return getJNDICoordinates(appName, moduleName, beanName, "", false);
	}
}
