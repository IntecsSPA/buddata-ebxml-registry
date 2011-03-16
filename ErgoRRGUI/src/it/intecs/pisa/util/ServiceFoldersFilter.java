/**
 * Filter for Service's folders
 */
package it.intecs.pisa.util;

/**
 * @author Massimiliano
 *
 */
public class ServiceFoldersFilter implements AbstractFileFilter{
	private String[] allowed=null;
	
	/*
	 * Constructor
	 */
	public ServiceFoldersFilter(String[] allowed)
	{
		this.allowed=allowed;
	}
	
	/*
	 * (non-Javadoc)
	 * @see it.intecs.pisa.util.AbstractFileFilter#acceptFile(java.lang.String)
	 */
	public boolean acceptFile(String path) {
		
		for(int u=0;u<allowed.length;u++)
		{
                    String ithPath=allowed[u];
			if(path.startsWith(ithPath))
				return true;
		}
		return false;
	}

}
