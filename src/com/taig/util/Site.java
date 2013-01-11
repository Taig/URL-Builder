/*
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details.
 */
package com.taig.util;

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
 * A Site is a programmatic representation of a website, managing its basic meta information to provide simple HTTP access.
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
	public Site( String scheme, String host )
	{
		this.scheme = scheme;
		this.host = host;
	}

	/**
	 * Construct a Site with a minimum of information (e.g. "http://example.org").
	 *
	 * @param scheme The Site's URL-scheme (e.g. {@link Scheme#HTTP} or {@link Scheme#HTTPS}).
	 * @param host   The Site's URL-host (e.g. "localhost" or "example").
	 */
	public Site( Scheme scheme, String host )
	{
		this( scheme.protocol, host );
	}

	/**
	 * Construct a Site from a {@link String}.
	 *
	 * @param url An URL; represented as a {@link String}.
	 * @throws MalformedURLException If the given URL-String is not a valid URL.
	 */
	public Site( String url ) throws MalformedURLException
	{
		this( new URL( url ) );
	}

	/**
	 * Construct a Site from an {@link URL}.
	 *
	 * @param url A valid {@link URL} object.
	 */
	public Site( URL url )
	{
		this.scheme = url.getProtocol();

		String[] hosts = url.getHost().split( "." );

		int i;

		for( i = 0; i < hosts.length - 2; i++ )
		{
			subdomains.add( hosts[i] );
		}

		String[] authentication = url.getUserInfo().split( ":", 2 );

		this.username = authentication[0];
		this.password = authentication[1];

		this.host = hosts[i] + hosts[i + 1];

		this.port = url.getPort() > 0 ? url.getPort() : url.getDefaultPort();

		String[] paths = url.getPath().split( "/" );

		for( i = 0; i < paths.length - 1; i++ )
		{
			this.paths.add( paths[i] );
		}

		if( paths[i].contains( "." ) )
		{
			this.file = paths[i];
		}
		else
		{
			this.paths.add( paths[i] );
		}

		String[] parameters = url.getQuery().split( "&" );

		for( i = 0; i < parameters.length; i++ )
		{
			String[] parameter = parameters[i].split( "=", 2 );
			this.parameters.put( parameter[0], parameter[1] );
		}

		this.fragment = url.getRef();
	}

	/**
	 * Construct a copy of the given {@link Site}.
	 *
	 * @param site A Site object that is used as template for the new Site.
	 */
	public Site( Site site )
	{
		this( site.scheme, site.host );
		this.charset = site.charset;
		this.username = site.username;
		this.password = site.password;
		this.subdomains.addAll( site.subdomains );
		this.port = site.port;
		this.paths.addAll( site.paths );
		this.file = site.file;
		this.parameters.putAll( site.parameters );
		this.fragment = site.fragment;
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
	 */
	public void setCharset( String charset )
	{
		this.charset = charset;
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
	 * @param scheme An URL-scheme (e.g. "http" or "https").
	 */
	public void setScheme( String scheme )
	{
		this.scheme = scheme;
	}

	/**
	 * Set the Site's URL-scheme.
	 *
	 * @param scheme An URL-{@link Scheme} (e.g. "http" or "https").
	 */
	public void setScheme( Scheme scheme )
	{
		this.scheme = scheme.getProtocol();
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
	 */
	public void setAuthentication( String username, String password )
	{
		this.username = username;
		this.password = password;
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
	 * @param subdomains URL-subdomains (e.g. "www").
	 */
	public void setSubdomains( List<String> subdomains )
	{
		this.subdomains = subdomains;
	}

	/**
	 * Add a subdomain to the Site's URL.
	 *
	 * @param subdomain An URL-subdomain (e.g. "www").
	 */
	public void addSubdomain( String subdomain )
	{
		this.subdomains.add( subdomain );
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
	 */
	public void setHost( String host )
	{
		this.host = host;
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
	 */
	public void setPort( int port )
	{
		this.port = port;
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
	 */
	public void setPaths( List<String> paths )
	{
		this.paths = paths;
	}

	/**
	 * Add a path to the Site's URL.
	 *
	 * @param path An URL-path (e.g. "home" or ["user", "taig"]).
	 */
	public void addPath( String path )
	{
		this.paths.add( path );
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
	 */
	public void setFile( String file )
	{
		this.file = file;
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
	 */
	public void setParameters( Map<String, String> params )
	{
		this.parameters = params;
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
	 * Add a parameter (<key, value>) to the Site's URL (e.g. "<id, 3>" or "<session, ASDF>"). If the key already exists it will be
	 * overridden.
	 *
	 * @param key   The parameter's key.
	 * @param value The parameter's value.
	 */
	public void putParameter( String key, String value )
	{
		this.parameters.put( key, value );
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
	 */
	public void setFragment( String fragment )
	{
		this.fragment = fragment;
	}

	/**
	 * Retrieve the Site's complete URL.
	 *
	 * @return A fully qualified URL (e.g. "http://www.example.org/home?user=taig").
	 */
	public URL getUrl()
	{
		try
		{
			return new URL( toString() );
		}
		catch( MalformedURLException exception )
		{
			return null;
		}
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
		StringBuilder urlBuilder = new StringBuilder( scheme ).append( "://" );

		if( username != null )
		{
			urlBuilder.append( username );

			if( password != null )
			{
				urlBuilder.append( ":" ).append( password );
			}

			urlBuilder.append( "@" );
		}

		for( String subdomain : subdomains )
		{
			urlBuilder.append( subdomain ).append( "." );
		}

		urlBuilder.append( host );

		if( port != 80 )
		{
			urlBuilder.append( ":" ).append( port );
		}

		for( String path : paths )
		{
			urlBuilder.append( "/" ).append( path );
		}

		if( file != null )
		{
			urlBuilder.append( "/" ).append( file );
		}

		if( !parameters.isEmpty() )
		{
			StringBuilder parametersBuilder = new StringBuilder();

			for( Entry<String, String> entry : parameters.entrySet() )
			{
				parametersBuilder.append( "&" );
				parametersBuilder.append( encode( entry.getKey() ) ).append( "=" ).append( encode( entry.getValue() ) );
			}

			urlBuilder.append( "?" ).append( parametersBuilder.deleteCharAt( 0 ) );
		}

		if( fragment != null )
		{
			urlBuilder.append( "#" ).append( encode( fragment ) );
		}

		return urlBuilder.toString();
	}
}