package com.github.lhervier.domino.spring.util;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class OsgiUtils {

	/**
	 * Retourne les éléments associés à un point d'extension
	 * @param id l'id du point d'extension
	 * @param cl la classe des objets attendus
	 * @return les objets
	 */
	@SuppressWarnings("unchecked")
	public static final <T> List<T> getExtensions(final String id, final Class<T> cl) {
		try {
			return AccessController.doPrivileged(new PrivilegedExceptionAction<List<T>>() {
				@Override
				public List<T> run() throws Exception {
					List<T> ret = new ArrayList<T>();
					IConfigurationElement[] elts = Platform.getExtensionRegistry().getConfigurationElementsFor(id);
					for( IConfigurationElement elt : elts ) {
						Object o = elt.createExecutableExtension("class");
						if( o == null )
							continue;
						if( !cl.isAssignableFrom(o.getClass()) )
							continue;
						ret.add((T) o);
					}
					return ret;
				}
			});
		} catch (PrivilegedActionException e) {
			throw new RuntimeException(e);
		}
	}
}
