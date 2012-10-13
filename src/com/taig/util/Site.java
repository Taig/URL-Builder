package com.taig.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A Site is a programmatic representation of a Website, managing its basic meta
 * information to provide simple access.
 */
public class Site
{
	public enum Scheme
	{
		HTTP( "http" ), HTTPS( "https" );

		private String	name;

		private Scheme( String name )
		{
			this.name = name;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}

	/**
	 * The charset that is used for encoding.
	 */
	private String				charset		= "UTF-8";

	/**
	 * The Site's URL-scheme (e.g. "http" or "https").
	 */
	private Scheme				scheme;

	/**
	 * The Site's URL-username.
	 */
	private String				username;

	/**
	 * The Site's URL-password.
	 */
	private String				password;

	/**
	 * The Site's URL-subdomains (e.g. "www").
	 */
	private List<String>		subdomains	= new ArrayList<String>( 3 );

	/**
	 * The Site's URL-host (e.g. "localhost" or "example.org").
	 */
	private String				host;

	/**
	 * The Site's URL-port (e.g. "80").
	 */
	private int					port		= 80;

	/**
	 * The Site's URL-paths (e.g. "home" or ["user", "taig"]).
	 */
	private List<String>		paths		= new ArrayList<String>( 3 );

	/**
	 * The Site's URL-file (e.g. "home.html").
	 */
	private String				file;

	/**
	 * The Site's URL-parameters (e.g. "<id, 3>" or "<session, ASDF>").
	 */
	private Map<String, String>	parameters	= new LinkedHashMap<String, String>( 3 );

	/**
	 * The Site's URL-fragment (e.g. "http://example.org#fragment").
	 */
	private String				fragement;

	/**
	 * Construct a Site with a minimum of information (e.g.
	 * "http://example.org").
	 * 
	 * @param scheme
	 *            The Site's URL-scheme (e.g. "http" or "https").
	 * @param host
	 *            The Site's URL-host (e.g. "localhost" or "example").
	 */
	public Site( Scheme scheme, String host )
	{
		setScheme( scheme );
		setHost( host );
	}

	/**
	 * Retrieve the charset that is used for encoding.
	 * 
	 * @return
	 */
	public String getCharset()
	{
		return charset;
	}

	/**
	 * Set the charset that is used for encoding.
	 * 
	 * @param charset
	 *            The charset that is used for encoding.
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setCharset( String charset )
	{
		this.charset = charset;
		return this;
	}

	/**
	 * Retrieve the Site's URL-scheme.
	 * 
	 * @return The Site's URL-scheme (e.g. "http" or "https").
	 */
	public Scheme getScheme()
	{
		return scheme;
	}

	/**
	 * Set the Site's URL-scheme.
	 * 
	 * @param scheme
	 *            An URL-scheme (e.g. "http" or "https").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setScheme( Scheme scheme )
	{
		this.scheme = scheme;
		return this;
	}

	/**
	 * Retrieve the Site's URL-username.
	 * 
	 * @return The Site's URL-username (as from "http://user:pass@example.org").
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Retrieve the Site's URL-password.
	 * 
	 * @return The Site's URL-password (as from "http://user:pass@example.org").
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Set the Site's URL-authentification.
	 * 
	 * @param username
	 *            The Site's URL-username (as from
	 *            "http://user:pass@example.org").
	 * @param password
	 *            The Site's URL-password (as from
	 *            "http://user:pass@example.org").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setAuthentification( String username, String password )
	{
		this.username = username;
		this.password = password;
		return this;
	}

	/**
	 * Retrieve the Site's URL-subdomains.
	 * 
	 * @return The Site's URL-subdomains (e.g. "www").
	 */
	public List<String> getSubdomains()
	{
		return subdomains;
	}

	/**
	 * Set the Site's URL-subdomains.
	 * 
	 * @param subdomains
	 *            URL-subdomains (e.g. "www").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setSubdomains( List<String> subdomains )
	{
		this.subdomains = subdomains;
		return this;
	}

	/**
	 * Add a subdomain to the Site's URL.
	 * 
	 * @param subdomain
	 *            An URL-subdomain (e.g. "www").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site addSubdomain( String subdomain )
	{
		this.subdomains.add( subdomain );
		return this;
	}

	/**
	 * Retrieve the Site's URL-host.
	 * 
	 * @return The Site's URL-host (e.g. "localhost" or "example.org").
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * Set the Site's URL-scheme.
	 * 
	 * @param scheme
	 *            An URL-host (e.g. "localhost" or "example.org").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setHost( String host )
	{
		this.host = host;
		return this;
	}

	/**
	 * Retrieve the Site's URL-port.
	 * 
	 * @return The Site's URL-port (e.g. "80").
	 */
	public int getPort()
	{
		return port;
	}

	/**
	 * Set the Site's URL-port.
	 * 
	 * @param port
	 *            An URL-port (e.g. "80");
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setPort( int port )
	{
		this.port = port;
		return this;
	}

	/**
	 * Retrieve the Site's URL-paths.
	 * 
	 * @return The Site's URL-paths (e.g. "home" or ["user", "taig"]).
	 */
	public List<String> getPaths()
	{
		return paths;
	}

	/**
	 * Set the Site's URL-paths.
	 * 
	 * @param paths
	 *            URL-paths (e.g. "home" or ["user", "taig"]).
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setPaths( List<String> paths )
	{
		this.paths = paths;
		return this;
	}

	/**
	 * Add a path to the Site's URL.
	 * 
	 * @param path
	 *            An URL-path (e.g. "home" or ["user", "taig"]).
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site addPath( String path )
	{
		this.paths.add( path );
		return this;
	}

	/**
	 * Retrieve the Site's URL-file (e.g. "home.html").
	 * 
	 * @return
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * Set the Site's URL-file (e.g. "home.html").
	 * 
	 * @param file
	 * @return
	 */
	public Site setFile( String file )
	{
		this.file = file;
		return this;
	}

	/**
	 * Retrieve the Site's URL-parameters.
	 * 
	 * @return The Site's URL-parameters (e.g. "<id, 3>" or "<session, ASDF>").
	 */
	public Map<String, String> getParameters()
	{
		return parameters;
	}

	/**
	 * Retrieve the Site's URL-parameter for the given key.
	 * 
	 * @param key
	 *            An URL-parameter.
	 * @return
	 */
	public String getParameter( String key )
	{
		return parameters.get( key );
	}

	/**
	 * Set the Site's URL-parameters.
	 * 
	 * @param params
	 *            URL-parameters (e.g. "<id, 3>" or "<session, ASDF>").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setParameters( HashMap<String, String> params )
	{
		this.parameters = params;
		return this;
	}

	/**
	 * Add a parameter (<key, value>) to the Site's URL (e.g. "<id, 3>" or
	 * "<session, ASDF>"). If the key already exists it will be overridden.
	 * 
	 * @param key
	 *            The param's key.
	 * @param value
	 *            The param's value.
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site putParameter( String key, String value )
	{
		this.parameters.put( key, value );
		return this;
	}

	/**
	 * Retrieve the Site's URL-fragment.
	 * 
	 * @return The Site's URL-fragment (e.g. "http://example.org#fragment").
	 */
	public String getFragment()
	{
		return fragement;
	}

	/**
	 * Set the Site's URL-fragment.
	 * 
	 * @param fragment
	 *            An URL-fragment (e.g. "http://example.org#fragment").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setFragment( String fragment )
	{
		this.fragement = fragment;
		return this;
	}

	/**
	 * Retrieve the Site's complete URL.
	 * 
	 * @return A fully qualified URL (e.g.
	 *         "http://www.example.org/home?user=taig").
	 */
	public URL getUrl() throws MalformedURLException
	{
		return new URL( toString() );
	}

	/**
	 * {@link URLEncoder#encode(String)} a String.
	 * 
	 * @param value
	 * @return
	 */
	private String encode( String value )
	{
		try
		{
			return URLEncoder.encode( value, charset );
		}
		catch( UnsupportedEncodingException exception )
		{
			exception.printStackTrace();
			return value;
		}
	}

	@Override
	public String toString()
	{
		StringBuilder urlBuilder = new StringBuilder( getScheme().toString() ).append( "://" );

		if( getUsername() != null )
		{
			urlBuilder.append( getUsername() );

			if( getPassword() != null )
			{
				urlBuilder.append( ":" ).append( getPassword() );
			}

			urlBuilder.append( "@" );
		}

		for( String subdomain : getSubdomains() )
		{
			urlBuilder.append( subdomain ).append( "." );
		}

		urlBuilder.append( getHost() );

		if( getPort() != 80 )
		{
			urlBuilder.append( ":" ).append( getPort() );
		}

		for( String path : getPaths() )
		{
			urlBuilder.append( "/" ).append( path );
		}

		if( getFile() != null )
		{
			urlBuilder.append( "/" ).append( getFile() );
		}

		if( !getParameters().isEmpty() )
		{
			StringBuilder parametersBuilder = new StringBuilder();

			for( Entry<String, String> entry : getParameters().entrySet() )
			{
				parametersBuilder.append( "&" );
				parametersBuilder.append( encode( entry.getKey() ) ).append( "=" ).append( encode( entry.getValue() ) );
			}

			urlBuilder.append( "?" ).append( parametersBuilder.deleteCharAt( 0 ) );
		}

		if( getFragment() != null )
		{
			urlBuilder.append( "#" ).append( encode( getFragment() ) );
		}

		return urlBuilder.toString();
	}
}