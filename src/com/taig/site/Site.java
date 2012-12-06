package com.taig.site;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A Site is a programmatic representation of a Website, managing its basic meta information to provide simple HTTP
 * access.
 */
public class Site
{
	/**
	 * All possible Site-Schemes. Currently only <code>HTTP</code> and <code>HTTPS</code> are supported.
	 */
	public enum Scheme
	{
		HTTP( "http" ), HTTPS( "https" );

		/**
		 * The actual protocol name as it's used in the URL.
		 */
		private String protocol;

		private Scheme( String protocol )
		{
			this.protocol = protocol;
		}

		/**
		 * Get the Scheme's protocol name as it is used in the URL.
		 *
		 * @return The Schemes protocol name (e.g. "http").
		 */
		public String getProtocol()
		{
			return protocol;
		}
	}

	/**
	 * The charset that is used for encoding.
	 */
	private String charset = "utf-8";

	/**
	 * The Site's URL-scheme (e.g. "http" or "https").
	 */
	private String scheme;

	/**
	 * The Site's URL-username.
	 */
	private String username;

	/**
	 * The Site's URL-password.
	 */
	private String password;

	/**
	 * The Site's URL-subdomains (e.g. "www").
	 */
	private List<String> subdomains = new ArrayList<String>( 3 );

	/**
	 * The Site's URL-host (e.g. "localhost" or "example.org").
	 */
	private String host;

	/**
	 * The Site's URL-port (e.g. "80").
	 */
	private int port = 80;

	/**
	 * The Site's URL-paths (e.g. "home" or ["user", "taig"]).
	 */
	private List<String> paths = new ArrayList<String>( 3 );

	/**
	 * The Site's URL-file (e.g. "home.html").
	 */
	private String file;

	/**
	 * The Site's URL-parameters (e.g. "<id, 3>" or "<session, ASDF>").
	 */
	private Map<String, String> parameters = new LinkedHashMap<String, String>( 3 );

	/**
	 * The Site's URL-fragment (e.g. "http://example.org#fragment").
	 */
	private String fragment;

	/**
	 * Construct a Site with a minimum of information (e.g. "http://example.org").
	 *
	 * @param scheme The Site's URL-scheme (e.g. "http" or "https").
	 * @param host   The Site's URL-host (e.g. "localhost" or "example").
	 */
	public Site( Scheme scheme, String host )
	{
		setScheme( scheme );
		setHost( host );
	}

	/**
	 * Construct a Site from an {@link URL}.
	 *
	 * @param url A valid {@link URL} object.
	 */
	public Site( URL url )
	{
		setScheme( url.getProtocol() );

		String[] hosts = url.getHost().split( "." );

		int i;

		for( i = 0; i < hosts.length - 2; i++ )
		{
			addSubdomain( hosts[i] );
		}

		String[] authentication = url.getUserInfo().split( ":", 2 );
		setAuthentication( authentication[0], authentication[1] );

		setHost( hosts[i] + hosts[i + 1] );

		setPort( url.getPort() > 0 ? url.getPort() : url.getDefaultPort() );

		String[] paths = url.getPath().split( "/" );

		for( i = 0; i < paths.length - 1; i++ )
		{
			addPath( paths[i] );
		}

		if( paths[i].contains( "." ) )
		{
			setFile( paths[i] );
		}
		else
		{
			addPath( paths[i] );
		}

		String[] parameters = url.getQuery().split( "&" );

		for( i = 0; i < parameters.length; i++ )
		{
			String[] parameter = parameters[i].split( "=", 2 );
			putParameter( parameter[0], parameter[1] );
		}

		setFragment( url.getRef() );
	}

	/**
	 * Construct a copy of the given {@link Site}.
	 *
	 * @param site A Site object that is used as template for the new Site.
	 */
	public Site( Site site )
	{
		setCharset( site.getCharset() );
		setScheme( site.getScheme() );
		setAuthentication( site.getUsername(), site.getPassword() );
		getSubdomains().addAll( site.getSubdomains() );
		setHost( site.getHost() );
		setPort( site.getPort() );
		getPaths().addAll( site.getPaths() );
		setFile( site.getFile() );
		getParameters().putAll( site.getParameters() );
		setFragment( site.getFragment() );
	}

	/**
	 * Retrieve the charset that is used for encoding.
	 *
	 * @return The Site's charset.
	 */
	public String getCharset()
	{
		return charset;
	}

	/**
	 * Set the charset that is used for encoding.
	 *
	 * @param charset The charset that is used for encoding.
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
	public String getScheme()
	{
		return scheme;
	}

	/**
	 * Set the Site's URL-scheme.
	 *
	 * @param scheme An URL-{@link Scheme} (e.g. "http" or "https").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setScheme( Scheme scheme )
	{
		this.scheme = scheme.getProtocol();
		return this;
	}

	/**
	 * Set the Site's URL-scheme.
	 *
	 * @param scheme An URL-scheme (e.g. "http" or "https").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setScheme( String scheme )
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
	 * Set the Site's URL-authentication.
	 *
	 * @param username The Site's URL-username (as from "http://user:pass@example.org").
	 * @param password The Site's URL-password (as from "http://user:pass@example.org").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setAuthentication( String username, String password )
	{
		this.username = username;
		this.password = password;
		return this;
	}

	/**
	 * Retrieve the Site's URL-subdomains
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
	 * @param subdomains URL-subdomains (e.g. "www").
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
	 * @param subdomain An URL-subdomain (e.g. "www").
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
	 * @param host An URL-host (e.g. "localhost" or "example.org").
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
	 * @param port An URL-port (e.g. "80");
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
	 * @param paths URL-paths (e.g. "home" or ["user", "taig"]).
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
	 * @param path An URL-path (e.g. "home" or ["user", "taig"]).
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
	 * @return The Site's file.
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * Set the Site's URL-file .
	 *
	 * @param file URL-file (e.g. "home.html").
	 * @return Current instance of {@link Site} to allow method chaining.
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
	 * Set the Site's URL-parameters.
	 *
	 * @param params URL-parameters (e.g. "<id, 3>" or "<session, ASDF>").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setParameters( Map<String, String> params )
	{
		this.parameters = params;
		return this;
	}

	/**
	 * Retrieve the Site's URL-parameter for the given key.
	 *
	 * @param key An URL-parameter.
	 * @return The parameter value that is associated with the given key or <code>null</code> if none exists.
	 */
	public String getParameter( String key )
	{
		return parameters.get( key );
	}

	/**
	 * Add a parameter (<key, value>) to the Site's URL (e.g. "<id, 3>" or "<session, ASDF>"). If the key already exists it
	 * will be overridden.
	 *
	 * @param key   The parameter's key.
	 * @param value The parameter's value.
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
		return fragment;
	}

	/**
	 * Set the Site's URL-fragment.
	 *
	 * @param fragment An URL-fragment (e.g. "http://example.org#fragment").
	 * @return Current instance of {@link Site} to allow method chaining.
	 */
	public Site setFragment( String fragment )
	{
		this.fragment = fragment;
		return this;
	}

	/**
	 * Retrieve the Site's complete URL.
	 *
	 * @return A fully qualified URL (e.g. "http://www.example.org/home?user=taig").
	 */
	public URL getUrl() throws MalformedURLException
	{
		return new URL( toString() );
	}

	/**
	 * {@link URLEncoder#encode(String)} a String.
	 *
	 * @param value The String that shall be URL-encoded.
	 * @return An URL-encoded String based on the {@link #charset} or the input value if the encoding fails.
	 */
	private String encode( String value )
	{
		try
		{
			return URLEncoder.encode( value, charset );
		}
		catch( UnsupportedEncodingException exception )
		{
			return value;
		}
	}

	@Override
	public boolean equals( Object object )
	{
		if( object != null && object instanceof Site )
		{
			Site site = (Site) object;

			return site.toString().equals( this.toString() );
		}

		return false;
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