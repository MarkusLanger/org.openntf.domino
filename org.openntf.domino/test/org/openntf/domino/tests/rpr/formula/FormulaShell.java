/* Generated By:JJTree&JavaCC: Do not edit this line. AtFormulaParser.java */
package org.openntf.domino.tests.rpr.formula;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import jline.ArgumentCompletor;
import jline.Completor;
import jline.ConsoleReader;
import jline.SimpleCompletor;

import lotus.domino.Session;

import org.openntf.domino.formula.AtFormulaParser;
import org.openntf.domino.formula.AtFunction;
import org.openntf.domino.formula.AtFunctionFactory;
import org.openntf.domino.formula.FormulaContext;
import org.openntf.domino.formula.ast.SimpleNode;
import org.openntf.domino.formula.impl.NotImplemented;
import org.openntf.domino.impl.Base;
import org.openntf.domino.thread.DominoThread;
import org.openntf.domino.utils.Factory;

public class FormulaShell implements Runnable {
	public static void main(final String[] args) {
		DominoThread thread = new DominoThread(new FormulaShell(), "My thread");
		thread.start();
	}

	public FormulaShell() {
		// whatever you might want to do in your constructor, but stay away from Domino objects
	}

	@Override
	public void run() {
		try {

			// RPr: I use  "http://jline.sourceforge.net/" to emulate a shell to test my formula engine
			// I put jline-1.0.jar in jvm/lib/ext
			// In detail: I do not know exactly what I'm doing here... I just need a shell :) 

			ConsoleReader reader = new ConsoleReader();
			reader.setBellEnabled(false);
			reader.setDebug(new PrintWriter(new FileWriter("writer.debug", true)));

			List<Completor> completors = new LinkedList<Completor>();

			// This code is responsible for autocompletion
			AtFunctionFactory funcFact = AtFunctionFactory.getInstance();
			Collection<AtFunction> funcs = funcFact.getFunctions().values();
			String[] autoComplete = new String[funcs.size()];
			int i = 0;
			for (AtFunction func : funcs) {
				if (func instanceof NotImplemented) {
					autoComplete[i++] = "NotImpl:" + func.getImage();
				} else {
					autoComplete[i++] = func.getImage() + "(";
				}
			}

			completors.add(new SimpleCompletor(autoComplete));
			reader.addCompletor(new ArgumentCompletor(completors));

			String line;
			// we want some more comfort
			File historyFile = new File("history.txt");
			reader.getHistory().setHistoryFile(historyFile);

			// now start the main loop
			System.out.println("This is the formula shell. Quit with 'q' ");
			while ((line = reader.readLine("$> ")) != null) {
				execute(line);
				if (line.equalsIgnoreCase("q")) {
					break;
				}
			}
			System.out.println("Bye.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		Factory.terminate();
		System.out.println(Factory.dumpCounters(true));
	}

	private void execute(final String line) {
		// TODO Auto-generated method stub

		List<Object> ntf = null;
		long time = System.currentTimeMillis();
		AtFormulaParser parser = AtFormulaParser.getInstance();

		try {
			SimpleNode n = parser.Parse(line);
			FormulaContext ctx = new FormulaContext(null, parser.getFormatter());
			ntf = n.evaluate(ctx);

			System.out.println("NTF:\t" + ntf);
		} catch (Exception e) {
			System.out.println("NTF failed!");
			e.printStackTrace();
		}

		List<Object> lotus = null;
		try {
			// We have to work on the lotus session!
			Session sess = Base.toLotus(Factory.getSession());
			lotus = sess.evaluate(line);
			System.out.println("LOTUS:\t" + lotus);

			boolean differs = false;

			if (ntf.size() == lotus.size()) {
				for (int i = 0; i < ntf.size(); i++) {
					Object a = ntf.get(i);
					Object b = lotus.get(i);
					if (a == null && b == null) {

					} else if (a == null || b == null) {
						differs = true;
						break;
					} else if (a instanceof Number && b instanceof Number) {
						if (Double.compare(((Number) a).doubleValue(), ((Number) b).doubleValue()) != 0) {
							differs = true;
							break;
						}
					} else if (!a.equals(b)) {
						differs = true;
						break;
					}
				}
			} else {
				differs = true;
			}
			if (differs) {
				System.out.println("!!! Different");
			} else {
				System.out.println("Both are equal");
			}
		} catch (Exception e) {
			System.out.println("DOMINO failed!");
			e.printStackTrace();
		}
	}
}
