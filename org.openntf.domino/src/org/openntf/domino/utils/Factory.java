/*
 * Copyright 2013
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */
package org.openntf.domino.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import lotus.domino.NotesException;
import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;

import org.openntf.domino.Base;
import org.openntf.domino.Document;
import org.openntf.domino.DocumentCollection;
import org.openntf.domino.Session;
import org.openntf.domino.Session.RunContext;
import org.openntf.domino.WrapperFactory;
import org.openntf.domino.exceptions.DataNotCompatibleException;
import org.openntf.domino.exceptions.UndefinedDelegateTypeException;
import org.openntf.domino.graph.DominoGraph;
import org.openntf.domino.logging.ConsoleFormatter;
import org.openntf.domino.logging.DefaultConsoleHandler;
import org.openntf.domino.logging.DefaultFileHandler;
import org.openntf.domino.logging.FileFormatter;
import org.openntf.domino.logging.OpenLogHandler;
import org.openntf.domino.types.DatabaseDescendant;
import org.openntf.domino.types.SessionDescendant;

// TODO: Auto-generated Javadoc
/**
 * The Enum Factory.
 */
public enum Factory {
	;

	private static ThreadLocal<WrapperFactory> currentWrapperFactory = new ThreadLocal<WrapperFactory>();

	private static ThreadLocal<ClassLoader> currentClassLoader_ = new ThreadLocal<ClassLoader>();

	private static ThreadLocal<Session> currentSessionHolder_ = new ThreadLocal<Session>();

	private static class SetupJob implements Runnable {
		public void run() {
			try {
				AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
					@Override
					public Object run() throws Exception {
						lotus.domino.Session session = NotesFactory.createSession();
						Factory.loadEnvironment(session);
						session.recycle();
						return null;
					}
				});
			} catch (AccessControlException e) {
				e.printStackTrace();
			} catch (PrivilegedActionException e) {
				e.printStackTrace();
			}

			try {
				AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
					@Override
					public Object run() throws Exception {
						String pattern = Factory.getDataPath() + "/IBM_TECHNICAL_SUPPORT/org.openntf.%u.%g.log";
						Logger oodLogger = Logger.getLogger("org.openntf.domino");
						oodLogger.setLevel(Level.WARNING);

						DefaultFileHandler dfh = new DefaultFileHandler(pattern, 50000, 100, true);
						dfh.setFormatter(new FileFormatter());
						dfh.setLevel(Level.WARNING);
						oodLogger.addHandler(dfh);

						DefaultConsoleHandler dch = new DefaultConsoleHandler();
						dch.setFormatter(new ConsoleFormatter());
						dch.setLevel(Level.WARNING);
						oodLogger.addHandler(dch);

						OpenLogHandler olh = new OpenLogHandler();
						olh.setLogDbPath("OpenLog.nsf");
						olh.setLevel(Level.WARNING);
						oodLogger.addHandler(olh);

						LogManager manager = LogManager.getLogManager();
						manager.addLogger(oodLogger);
						return null;
					}
				});
			} catch (AccessControlException e) {
				e.printStackTrace();
			} catch (PrivilegedActionException e) {
				e.printStackTrace();
			}
		}
	}

	static {
		NotesThread nt = new NotesThread(new SetupJob());
		nt.start();
	}

	private static Map<String, String> ENVIRONMENT;
	private static boolean session_init = false;
	private static boolean jar_init = false;

	public static void loadEnvironment(final lotus.domino.Session session) {
		if (ENVIRONMENT == null) {
			ENVIRONMENT = new HashMap<String, String>();
		}
		if (session != null && !session_init) {
			try {
				AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
					@Override
					public Object run() throws Exception {
						try {
							ENVIRONMENT.put("directory", session.getEnvironmentString("Directory", true));
							ENVIRONMENT.put("notesprogram", session.getEnvironmentString("NotesProgram", true));
							ENVIRONMENT.put("kittype", session.getEnvironmentString("KitType", true));
							ENVIRONMENT.put("servicename", session.getEnvironmentString("ServiceName", true));
							ENVIRONMENT.put("httpjvmmaxheapsize", session.getEnvironmentString("HTTPJVMMaxHeapSize", true));
							ENVIRONMENT.put("dominocontrollercurrentlog", session.getEnvironmentString("DominoControllerCurrentLog", true));
						} catch (NotesException ne) {
							ne.printStackTrace();
						}
						return null;
					}
				});
			} catch (AccessControlException e) {
				e.printStackTrace();
			} catch (PrivilegedActionException e) {
				e.printStackTrace();
			}
			session_init = true;
		}
		if (!jar_init) {
			try {
				AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
					@Override
					public Object run() throws Exception {
						try {
							InputStream inputStream = Factory.class.getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");
							if (inputStream != null) {
								Manifest mani;
								mani = new Manifest(inputStream);
								Attributes attrib = mani.getMainAttributes();
								ENVIRONMENT.put("version", attrib.getValue("Implementation-Version"));
								ENVIRONMENT.put("title", attrib.getValue("Implementation-Title"));
								ENVIRONMENT.put("url", attrib.getValue("Implementation-Vendor-URL"));
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
				});
			} catch (AccessControlException e) {
				e.printStackTrace();
			} catch (PrivilegedActionException e) {
				e.printStackTrace();
			}
			jar_init = true;
		}
	}

	public static String getEnvironment(final String key) {
		if (ENVIRONMENT == null) {
			loadEnvironment(null);
		}
		return ENVIRONMENT.get(key);
	}

	public static String getTitle() {
		return getEnvironment("title");
	}

	public static String getUrl() {
		return getEnvironment("url");
	}

	public static String getVersion() {
		return getEnvironment("version");
	}

	public static String getDataPath() {
		return getEnvironment("directory");
	}

	public static String getProgramPath() {
		return getEnvironment("notesprogram");
	}

	public static String getHTTPJVMHeapSize() {
		return getEnvironment("httpjvmheapsize");
	}

	/** The Constant log_. */
	private static final Logger log_ = Logger.getLogger(Factory.class.getName());

	/** The Constant TRACE_COUNTERS. */
	private static final boolean TRACE_COUNTERS = true;
	/** use a separate counter in each thread */
	private static final boolean COUNT_PER_THREAD = true;

	/** The lotus counter. */
	private static Counter lotusCounter = new Counter(COUNT_PER_THREAD);

	/** The recycle err counter. */
	private static Counter recycleErrCounter = new Counter(COUNT_PER_THREAD);

	/** The auto recycle counter. */
	private static Counter autoRecycleCounter = new Counter(COUNT_PER_THREAD);

	/**
	 * Gets the lotus count.
	 * 
	 * @return the lotus count
	 */
	public static int getLotusCount() {
		return lotusCounter.intValue();
	}

	/**
	 * Count a created lotus element.
	 */
	public static void countLotus() {
		if (TRACE_COUNTERS)
			lotusCounter.increment();
	}

	/**
	 * Gets the recycle error count.
	 * 
	 * @return the recycle error count
	 */
	public static int getRecycleErrorCount() {
		return recycleErrCounter.intValue();
	}

	/**
	 * Count recycle error.
	 */
	public static void countRecycleError() {
		if (TRACE_COUNTERS)
			recycleErrCounter.increment();
	}

	/**
	 * Gets the auto recycle count.
	 * 
	 * @return the auto recycle count
	 */
	public static int getAutoRecycleCount() {
		return autoRecycleCounter.intValue();
	}

	/**
	 * Count auto recycle.
	 * 
	 * @return the int
	 */
	public static int countAutoRecycle() {
		if (TRACE_COUNTERS) {
			return autoRecycleCounter.increment();
		} else {
			return 0;
		}
	}

	public static RunContext getRunContext() {
		// TODO finish this implementation, which needs a lot of work.
		RunContext result = RunContext.UNKNOWN;
		SecurityManager sm = System.getSecurityManager();
		if (sm == null)
			return RunContext.CLI;

		Object o = sm.getSecurityContext();
		if (log_.isLoggable(Level.INFO))
			log_.log(Level.INFO, "SecurityManager is " + sm.getClass().getName() + " and context is " + o.getClass().getName());
		if (sm instanceof lotus.notes.AgentSecurityManager) {
			lotus.notes.AgentSecurityManager asm = (lotus.notes.AgentSecurityManager) sm;
			Object xsm = asm.getExtenderSecurityContext();
			if (xsm instanceof lotus.notes.AgentSecurityContext) {
				lotus.notes.AgentSecurityContext nasc = (lotus.notes.AgentSecurityContext) xsm;
			}
			Object asc = asm.getSecurityContext();
			if (asc != null) {
				// System.out.println("Security context is " + asc.getClass().getName());
			}
			// ThreadGroup tg = asm.getThreadGroup();
			// System.out.println("ThreadGroup name: " + tg.getName());

			result = RunContext.AGENT;
		}
		com.ibm.domino.http.bootstrap.logger.RCPLoggerConfig rcplc;
		ClassLoader cl = com.ibm.domino.http.bootstrap.BootstrapClassLoader.getSharedClassLoader();
		if (cl instanceof com.ibm.domino.http.bootstrap.BootstrapOSGIClassLoader) {
			com.ibm.domino.http.bootstrap.BootstrapOSGIClassLoader bocl = (com.ibm.domino.http.bootstrap.BootstrapOSGIClassLoader) cl;
			result = RunContext.XPAGES_OSGI;
		}

		return result;
	}

	/**
	 * returns the wrapper factory for this thread
	 * 
	 * @return
	 */
	public static WrapperFactory getWrapperFactory() {
		return currentWrapperFactory.get();
	}

	// --- session handling 
	/**
	 * Wraps and caches sessions. Sessions are put in a separate map (otherwise you can use fromLotusObject). (you may overwrite this if we
	 * make a non static IFactory)
	 * 
	 * @param lotus
	 * @param parent
	 * @return
	 */
	public static Session fromLotusSession(final lotus.domino.Session lotus, final Base parent) {
		return getWrapperFactory().fromLotusSession(lotus, parent);
	}

	/**
	 * Wraps & caches a lotus.domino.Document
	 * 
	 * @param lotus
	 * @param parent
	 * @return
	 */
	public static Document fromLotusDocument(final lotus.domino.Document lotus, final Base parent) {
		return getWrapperFactory().fromLotusDocument(lotus, parent);
	}

	// --- others
	/**
	 * Wraps & caches all lotus object except Names, DateTimes, Sessions, Documents
	 * 
	 * @param lotus
	 * @param parent
	 * @return
	 */
	public static Base fromLotusObject(final lotus.domino.Base lotus, final Base parent) {
		return getWrapperFactory().fromLotusObject(lotus, parent);
	}

	/**
	 * From lotus.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param lotus
	 *            the lotus
	 * @param T
	 *            the t
	 * @param parent
	 *            the parent
	 * @return the t
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T fromLotus(final lotus.domino.Base lotus, final Class<? extends Base> T, final Base parent) {
		return getWrapperFactory().fromLotus(lotus, T, parent);
	}

	/**
	 * From lotus.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param lotusColl
	 *            the lotus coll
	 * @param T
	 *            the t
	 * @param parent
	 *            the parent
	 * @return the collection
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Collection<T> fromLotus(final Collection<?> lotusColl, final Class<? extends Base> T, final Base<?> parent) {
		Collection<T> result = new ArrayList<T>(); // TODO anyone got a better implementation?
		WrapperFactory wf = getWrapperFactory();

		if (!lotusColl.isEmpty()) {
			for (Object lotus : lotusColl) {
				if (lotus instanceof lotus.domino.Base) {
					result.add((T) wf.fromLotus((lotus.domino.Base) lotus, T, parent));
				}
			}
		}
		return result;

	}

	/**
	 * From lotus as vector.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param lotusColl
	 *            the lotus coll
	 * @param T
	 *            the t
	 * @param parent
	 *            the parent
	 * @return the org.openntf.domino.impl. vector
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> org.openntf.domino.impl.Vector<T> fromLotusAsVector(final Collection<?> lotusColl,
			final Class<? extends org.openntf.domino.Base> T, final org.openntf.domino.Base<?> parent) {
		org.openntf.domino.impl.Vector<T> result = new org.openntf.domino.impl.Vector<T>(); // TODO anyone got a better implementation?
		WrapperFactory wf = getWrapperFactory();

		if (!lotusColl.isEmpty()) {
			for (Object lotus : lotusColl) {
				if (lotus instanceof lotus.domino.local.NotesBase) {
					result.add((T) wf.fromLotus((lotus.domino.Base) lotus, T, parent));
				}
			}
		}
		return result;

	}

	/**
	 * Wrap column values.
	 * 
	 * @param values
	 *            the values
	 * @return the java.util. vector
	 */
	public static java.util.Vector<Object> wrapColumnValues(final Collection<?> values, final org.openntf.domino.Session session) {
		if (values == null) {
			return null;
		}
		int i = 0;
		WrapperFactory wf = getWrapperFactory();
		java.util.Vector<Object> result = new org.openntf.domino.impl.Vector<Object>();
		for (Object value : values) {
			if (value == null) {
				result.add(null);
			} else if (value instanceof lotus.domino.DateTime) {
				Object wrapped = null;
				try {
					wrapped = wf.fromLotus((lotus.domino.DateTime) value, org.openntf.domino.DateTime.class, session);
				} catch (Throwable t) {
					if (t instanceof NotesException) {
						String text = ((NotesException) t).text;
						System.out.println("Unable to wrap a DateTime found in Vector member " + i + " of " + values.size() + " because "
								+ text);
						try {
							lotus.domino.DateTime dt = (lotus.domino.DateTime) value;
							String gmttime = dt.getGMTTime();
							System.out.println("GMTTime: " + gmttime);
						} catch (Exception e) {

						}
					}

				}
				if (wrapped == null) {
					result.add("");
				} else {
					result.add(wrapped);
				}
			} else if (value instanceof lotus.domino.DateRange) {
				result.add(fromLotus((lotus.domino.DateRange) value, org.openntf.domino.DateRange.class, session));
			} else if (value instanceof Collection) {
				result.add(wrapColumnValues((Collection<?>) value, session));
			} else {
				result.add(value);
			}
			i++;
		}
		return result;
	}

	//
	//	/**
	//	 * Wrapped evaluate.
	//	 * 
	//	 * @param session
	//	 *            the session
	//	 * @param formula
	//	 *            the formula
	//	 * @return the java.util. vector
	//	 */
	//	public static java.util.Vector<Object> wrappedEvaluate(final org.openntf.domino.Session session, final String formula) {
	//		java.util.Vector<Object> result = new org.openntf.domino.impl.Vector<Object>();
	//		java.util.Vector<Object> values = session.evaluate(formula);
	//		for (Object value : values) {
	//			if (value instanceof lotus.domino.DateTime) {
	//				result.add(fromLotus((lotus.domino.DateTime) value, org.openntf.domino.impl.DateTime.class, session));
	//			} else if (value instanceof lotus.domino.DateRange) {
	//				result.add(fromLotus((lotus.domino.DateRange) value, org.openntf.domino.impl.DateRange.class, session));
	//			} else if (value instanceof Collection) {
	//				result.add(wrapColumnValues((Collection<?>) value, session));
	//			} else {
	//				result.add(value);
	//			}
	//		}
	//		return result;
	//	}
	//
	//	/**
	//	 * Wrapped evaluate.
	//	 * 
	//	 * @param session
	//	 *            the session
	//	 * @param formula
	//	 *            the formula
	//	 * @param contextDocument
	//	 *            the context document
	//	 * @return the java.util. vector
	//	 */
	//	public static java.util.Vector<Object> wrappedEvaluate(final org.openntf.domino.Session session, final String formula,
	//			final lotus.domino.Document contextDocument) {
	//		java.util.Vector<Object> result = new org.openntf.domino.impl.Vector<Object>();
	//		java.util.Vector<Object> values = session.evaluate(formula, contextDocument);
	//		for (Object value : values) {
	//			if (value instanceof lotus.domino.DateTime) {
	//				result.add(fromLotus((lotus.domino.DateTime) value, org.openntf.domino.impl.DateTime.class, session));
	//			} else if (value instanceof lotus.domino.DateRange) {
	//				result.add(fromLotus((lotus.domino.DateRange) value, org.openntf.domino.impl.DateRange.class, session));
	//			} else if (value instanceof Collection) {
	//				result.add(wrapColumnValues((Collection<?>) value, session));
	//			} else {
	//				result.add(value);
	//			}
	//		}
	//		return result;
	//	}

	/**
	 * Gets the session.
	 * 
	 * @return the session
	 */
	public static org.openntf.domino.Session getSession() {
		org.openntf.domino.Session result = currentSessionHolder_.get();
		if (result == null) {
			try {
				result = Factory.fromLotus(lotus.domino.NotesFactory.createSession(), Session.class, null);
			} catch (lotus.domino.NotesException ne) {
				try {
					result = XSPUtil.getCurrentSession();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			setSession(result);
		}
		if (result == null) {
			System.out
					.println("SEVERE: Unable to get default session. This probably means that you are running in an unsupported configuration or you forgot to set up your context at the start of the operation. If you're running in XPages, check the xsp.properties of your database. If you are running in an Agent, make sure you start with a call to Factory.fromLotus() and pass in your lotus.domino.Session");
			Throwable t = new Throwable();
			t.printStackTrace();
		}
		return result;
	}

	public static org.openntf.domino.Session getSession_unchecked() {
		return currentSessionHolder_.get();
	}

	public static void setSession(final lotus.domino.Session session) {
		currentSessionHolder_.set((Session) fromLotus(session, org.openntf.domino.Session.class, null));
	}

	public static void clearSession() {
		currentSessionHolder_.set(null);
	}

	public static ClassLoader getClassLoader() {
		if (currentClassLoader_.get() == null) {
			ClassLoader loader = null;
			try {
				loader = AccessController.doPrivileged(new PrivilegedExceptionAction<ClassLoader>() {
					@Override
					public ClassLoader run() throws Exception {
						return Thread.currentThread().getContextClassLoader();
					}
				});
			} catch (AccessControlException e) {
				e.printStackTrace();
			} catch (PrivilegedActionException e) {
				e.printStackTrace();
			}
			setClassLoader(loader);
		}
		return currentClassLoader_.get();
	}

	public static void setClassLoader(final ClassLoader loader) {
		if (loader != null) {
			//			System.out.println("Setting OpenNTF Factory ClassLoader to a " + loader.getClass().getName());
		}
		currentClassLoader_.set(loader);
	}

	public static void clearClassLoader() {
		currentClassLoader_.set(null);
	}

	public static void clearDominoGraph() {
		DominoGraph.clearDocumentCache();
	}

	public static void clearBubbleExceptions() {
		DominoUtils.setBubbleExceptions(null);
	}

	public static lotus.domino.Session terminate() {
		lotus.domino.Session result = null;
		WrapperFactory wf = getWrapperFactory();
		if (currentSessionHolder_.get() != null) {
			result = (lotus.domino.Session) wf.toLotus(currentSessionHolder_.get());
		}
		getWrapperFactory().terminate();

		clearSession();
		clearClassLoader();
		clearBubbleExceptions();
		clearDominoGraph();
		return result;
	}

	/**
	 * Gets the session full access.
	 * 
	 * @return the session full access
	 */
	public static org.openntf.domino.Session getSessionFullAccess() {
		try {
			Object result = AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
				@Override
				public Object run() throws Exception {
					lotus.domino.Session s = lotus.domino.NotesFactory.createSessionWithFullAccess();
					return fromLotus(s, org.openntf.domino.Session.class, null);
				}
			});
			if (result instanceof org.openntf.domino.Session) {
				return (org.openntf.domino.Session) result;
			}
		} catch (PrivilegedActionException e) {
			DominoUtils.handleException(e);
		}
		return null;
	}

	/**
	 * Gets the trusted session.
	 * 
	 * @return the trusted session
	 */
	public static org.openntf.domino.Session getTrustedSession() {
		try {
			Object result = AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
				@Override
				public Object run() throws Exception {
					lotus.domino.Session s = lotus.domino.NotesFactory.createTrustedSession();
					return fromLotus(s, org.openntf.domino.Session.class, null);
				}
			});
			if (result instanceof org.openntf.domino.Session) {
				return (org.openntf.domino.Session) result;
			}
		} catch (PrivilegedActionException e) {
			DominoUtils.handleException(e);
		}
		return null;
	}

	/**
	 * Gets the parent database.
	 * 
	 * @param base
	 *            the base
	 * @return the parent database
	 */
	public static org.openntf.domino.Database getParentDatabase(final org.openntf.domino.Base<?> base) {
		org.openntf.domino.Database result = null;
		if (base instanceof org.openntf.domino.Database) {
			result = (org.openntf.domino.Database) base;
		} else if (base instanceof DatabaseDescendant) {
			result = ((DatabaseDescendant) base).getAncestorDatabase();
		} else {
			throw new UndefinedDelegateTypeException();
		}
		return result;
	}

	/**
	 * Gets the session.
	 * 
	 * @param base
	 *            the base
	 * @return the session
	 */
	public static org.openntf.domino.Session getSession(final org.openntf.domino.Base<?> base) {
		org.openntf.domino.Session result = null;
		if (base instanceof SessionDescendant) {
			result = ((SessionDescendant) base).getAncestorSession();
		} else if (base instanceof org.openntf.domino.Session) {
			result = (org.openntf.domino.Session) base;
		} else {
			System.out.println("couldn't find session for object of type " + base.getClass().getName());
			throw new UndefinedDelegateTypeException();
		}
		if (result == null)
			result = org.openntf.domino.impl.Session.getDefaultSession(); // last ditch, get the primary Session;
		return result;
	}

	// public static boolean toBoolean(Object value) {
	// if (value instanceof String) {
	// char[] c = ((String) value).toCharArray();
	// if (c.length > 1 || c.length == 0) {
	// return false;
	// } else {
	// return c[0] == '1';
	// }
	// } else if (value instanceof Double) {
	// if (((Double) value).intValue() == 0) {
	// return false;
	// } else {
	// return true;
	// }
	// } else {
	// throw new DataNotCompatibleException("Cannot convert a " + value.getClass().getName() + " to boolean primitive.");
	// }
	// }
	//
	// public static int toInt(Object value) {
	// if (value instanceof Integer) {
	// return ((Integer) value).intValue();
	// } else if (value instanceof Double) {
	// return ((Double) value).intValue();
	// } else {
	// throw new DataNotCompatibleException("Cannot convert a " + value.getClass().getName() + " to int primitive.");
	// }
	// }
	//
	// public static double toDouble(Object value) {
	// if (value instanceof Integer) {
	// return ((Integer) value).doubleValue();
	// } else if (value instanceof Double) {
	// return ((Double) value).doubleValue();
	// } else {
	// throw new DataNotCompatibleException("Cannot convert a " + value.getClass().getName() + " to double primitive.");
	// }
	// }
	//
	// public static long toLong(Object value) {
	// if (value instanceof Integer) {
	// return ((Integer) value).longValue();
	// } else if (value instanceof Double) {
	// return ((Double) value).longValue();
	// } else {
	// throw new DataNotCompatibleException("Cannot convert a " + value.getClass().getName() + " to long primitive.");
	// }
	// }
	//
	// public static short toShort(Object value) {
	// if (value instanceof Integer) {
	// return ((Integer) value).shortValue();
	// } else if (value instanceof Double) {
	// return ((Double) value).shortValue();
	// } else {
	// throw new DataNotCompatibleException("Cannot convert a " + value.getClass().getName() + " to short primitive.");
	// }
	//
	// }
	//
	// public static float toFloat(Object value) {
	// if (value instanceof Integer) {
	// return ((Integer) value).floatValue();
	// } else if (value instanceof Double) {
	// return ((Double) value).floatValue();
	// } else {
	// throw new DataNotCompatibleException("Cannot convert a " + value.getClass().getName() + " to float primitive.");
	// }
	//
	// }
	//
	// public static Object toPrimitive(Vector<Object> values, Class<?> ctype) {
	// if (ctype.isPrimitive()) {
	// throw new DataNotCompatibleException(ctype.getName() + " is not a primitive type.");
	// }
	// if (values.size() > 1) {
	// throw new DataNotCompatibleException("Cannot create a primitive " + ctype + " from data because we have a multiple values.");
	// }
	// if (values.isEmpty()) {
	// throw new DataNotCompatibleException("Cannot create a primitive " + ctype + " from data because we don't have any values.");
	// }
	// if (ctype == Boolean.TYPE)
	// return toBoolean(values.get(0));
	// if (ctype == Integer.TYPE)
	// return toInt(values.get(0));
	// if (ctype == Short.TYPE)
	// return toShort(values.get(0));
	// if (ctype == Long.TYPE)
	// return toLong(values.get(0));
	// if (ctype == Float.TYPE)
	// return toFloat(values.get(0));
	// if (ctype == Double.TYPE)
	// return toDouble(values.get(0));
	// if (ctype == Byte.TYPE)
	// throw new UnimplementedException("Primitive conversion for byte not yet defined");
	// if (ctype == Character.TYPE)
	// throw new UnimplementedException("Primitive conversion for char not yet defined");
	// throw new DataNotCompatibleException("");
	// }
	//
	// public static String join(Collection<Object> values, String separator) {
	// StringBuilder sb = new StringBuilder();
	// Iterator<Object> it = values.iterator();
	// while (it.hasNext()) {
	// sb.append(String.valueOf(it.next()));
	// if (it.hasNext())
	// sb.append(separator);
	// }
	// return sb.toString();
	// }
	//
	// public static String join(Collection<Object> values) {
	// return join(values, ", ");
	// }
	//
	// public static Object toPrimitiveArray(Vector<Object> values, Class<?> ctype) throws DataNotCompatibleException {
	// Object result = null;
	// int size = values.size();
	// if (ctype == Boolean.TYPE) {
	// boolean[] outcome = new boolean[size];
	// // TODO NTF - should allow for String fields that are binary sequences: "1001001" (SOS)
	// for (int i = 0; i < size; i++) {
	// Object o = values.get(i);
	// outcome[i] = toBoolean(o);
	// }
	// result = outcome;
	// } else if (ctype == Byte.TYPE) {
	// byte[] outcome = new byte[size];
	// // TODO
	// result = outcome;
	// } else if (ctype == Character.TYPE) {
	// char[] outcome = new char[size];
	// // TODO How should this work? Just concatenate the char arrays for each String?
	// result = outcome;
	// } else if (ctype == Short.TYPE) {
	// short[] outcome = new short[size];
	// for (int i = 0; i < size; i++) {
	// Object o = values.get(i);
	// outcome[i] = toShort(o);
	// }
	// result = outcome;
	// } else if (ctype == Integer.TYPE) {
	// int[] outcome = new int[size];
	// for (int i = 0; i < size; i++) {
	// Object o = values.get(i);
	// outcome[i] = toInt(o);
	// }
	// result = outcome;
	// } else if (ctype == Long.TYPE) {
	// long[] outcome = new long[size];
	// for (int i = 0; i < size; i++) {
	// Object o = values.get(i);
	// outcome[i] = toLong(o);
	// }
	// result = outcome;
	// } else if (ctype == Float.TYPE) {
	// float[] outcome = new float[size];
	// for (int i = 0; i < size; i++) {
	// Object o = values.get(i);
	// outcome[i] = toFloat(o);
	// }
	// result = outcome;
	// } else if (ctype == Double.TYPE) {
	// double[] outcome = new double[size];
	// for (int i = 0; i < size; i++) {
	// Object o = values.get(i);
	// outcome[i] = toDouble(o);
	// }
	// result = outcome;
	// }
	// return result;
	// }
	//
	// public static Date toDate(Object value) throws DataNotCompatibleException {
	// if (value == null)
	// return null;
	// if (value instanceof Long) {
	// return new Date(((Long) value).longValue());
	// } else if (value instanceof String) {
	// // TODO finish
	// DateFormat df = new SimpleDateFormat();
	// try {
	// return df.parse((String) value);
	// } catch (ParseException e) {
	// throw new DataNotCompatibleException("Cannot create a Date from String value " + (String) value);
	// }
	// } else if (value instanceof lotus.domino.DateTime) {
	// return DominoUtils.toJavaDateSafe((lotus.domino.DateTime) value);
	// } else {
	// throw new DataNotCompatibleException("Cannot create a Date from a " + value.getClass().getName());
	// }
	// }
	//
	// public static Date[] toDates(Collection<Object> vector) throws DataNotCompatibleException {
	// if (vector == null)
	// return null;
	//
	// Date[] result = new Date[vector.size()];
	// int i = 0;
	// for (Object o : vector) {
	// result[i++] = toDate(o);
	// }
	// return result;
	// }
	//
	// public static org.openntf.domino.DateTime[] toDateTimes(Collection<Object> vector, org.openntf.domino.Session session)
	// throws DataNotCompatibleException {
	// if (vector == null)
	// return null;
	//
	// org.openntf.domino.DateTime[] result = new org.openntf.domino.DateTime[vector.size()];
	// int i = 0;
	// for (Object o : vector) {
	// result[i++] = session.createDateTime(toDate(o));
	// }
	// return result;
	// }
	//
	// public static org.openntf.domino.Name[] toNames(Collection<Object> vector, org.openntf.domino.Session session)
	// throws DataNotCompatibleException {
	// if (vector == null)
	// return null;
	//
	// org.openntf.domino.Name[] result = new org.openntf.domino.Name[vector.size()];
	// int i = 0;
	// for (Object o : vector) {
	// result[i++] = session.createName(String.valueOf(o));
	// }
	// return result;
	// }
	//
	// public static String[] toStrings(Collection<Object> vector) throws DataNotCompatibleException {
	// if (vector == null)
	// return null;
	// String[] strings = new String[vector.size()];
	// int i = 0;
	// for (Object o : vector) {
	// if (o instanceof DateTime) {
	// strings[i++] = ((DateTime) o).getGMTTime();
	// } else {
	// strings[i++] = String.valueOf(o);
	// }
	// }
	// return strings;
	// }

	/**
	 * To lotus note collection.
	 * 
	 * @param collection
	 *            the collection
	 * @return the org.openntf.domino. note collection
	 */
	public static org.openntf.domino.NoteCollection toNoteCollection(final lotus.domino.DocumentCollection collection) {
		org.openntf.domino.NoteCollection result = null;
		if (collection instanceof DocumentCollection) {
			org.openntf.domino.Database db = ((DocumentCollection) collection).getParent();
			result = db.createNoteCollection(false);
			result.add((DocumentCollection) collection);
		} else {
			throw new DataNotCompatibleException("Cannot convert a non-OpenNTF DocumentCollection to a NoteCollection");
		}
		return result;
	}

}
