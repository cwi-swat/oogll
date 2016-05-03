package oogll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import oogll.forest.Flattener;
import oogll.sppf.Node;
import oogll.symbol.Char;
import oogll.symbol.Iter;
import oogll.symbol.IterStar;
import oogll.symbol.NonTerminal;
import oogll.symbol.RegExp;

public class Test {

	public static void main(String[] args) throws IOException {
//		benchmarkGamma2(10, 100, 5);
//		gamma2(50);
//		horizAmb();
//		expressions();
//		iterIter();
//		iter();
//		expressionsDeep();
		jurgen();
//		empty();
//		chain();
//		cycle();
//		cycle2();
	}


	private static void benchmarkGamma2(int start, int end, int step) throws IOException {
		int n = 0;
		File f;
		do {
			f = new File("data/gamma2-" + start + "-to-" + end + "-by-" + step + "-" + n++ + ".dat");	
		} while (f.exists());
		
		FileWriter w = new FileWriter(f);
		for (int i = start; i < end; i += step) {
			System.gc();
			System.out.println("Measuring n = " + i);
			long t = System.currentTimeMillis();
			gamma2(i);
			long d = System.currentTimeMillis() - t;
			System.out.println(d);
			w.write(i + " " + d + "\n");
			w.flush();
		}
		w.close();
	}

	private static void gamma2(int n) {
		NonTerminal gamma2 = new NonTerminal("S");
		gamma2.addAlt(new Alt(new Char('b')));
		gamma2.addAlt(new Alt(gamma2, gamma2));
		gamma2.addAlt(new Alt(gamma2, gamma2, gamma2));

		String src = "";
		for (int i = 0; i < n; i++) {
			src += "b";
		}
		System.out.println("Src = " + src);
		GLL d = new GLL(gamma2);
		d.parse(src);
		d.printStatistics();
	}
	

	private static void chain() {
		NonTerminal s = new NonTerminal("Exp");
		s.addAlt(new Alt(new RegExp("[a-z]+")));
		NonTerminal jurgen = new NonTerminal("Jurgen");
		jurgen.addAlt(new Alt(s));
		
		String src = "a";
		System.out.println("Src = " + src);
		GLL d = new GLL(jurgen);
		d.parse(src);
	}
	
	private static void jurgen() throws IOException {
		NonTerminal e = new NonTerminal("Exp");
		NonTerminal s = new NonTerminal("Stat");
		NonTerminal b = new NonTerminal("Block");

		e.addAlt(new Alt(new RegExp("[a-z]+")));
		e.addAlt(new Alt(new RegExp("[0-9]+")));

		Alt plus = new Alt(e, new Char('+'), e);
		e.addAlt(plus);
		
		Alt min = new Alt(e, new Char('-'), e);
		e.addAlt(min);

		Alt mul = new Alt(e, new Char('*'), e);
		e.addAlt(mul);
		
		Alt div = new Alt(e, new Char('/'), e);
		e.addAlt(div);

		Alt eq = new Alt(e, new Char('='), e);
		e.addAlt(eq);
		
		for (int i = 0; i < 60; i++) {
			Alt x = new Alt(e, new Char('+'), e);
			e.addAlt(x);
		}
		
		s.addAlt(new Alt(e, new Char(';')));
		
		b.addAlt(new Alt(new Char('{'), new IterStar(s), new Char('}')));
		
		GLL d = new GLL(b);
		Node x = null;
		File file = new File("jurgen.dat");
		FileWriter writer = new FileWriter(file);
		for (int i = 1; i < 50; i++) {
			String src = "{";
			for (int j = 0; j < i; j++) {
				src += "x=1;";
			}
			src += "}";

			long before = System.currentTimeMillis();
			x = d.parse(src);
			long after = System.currentTimeMillis();
			writer.write(i + " " + (after - before) / 1000.0 + "\n");
			writer.flush();
		}
		writer.close();
		
		
//		System.out.println(x.toDot());
//		writeDot("exp1.dot", x.toDot());
//		writeDot("exp2.dot", Flattener.flatten(x).toDot());
		d.printStatistics();
		
		
	}
	
	private static void expressions() {
		NonTerminal s = new NonTerminal("Exp");
		s.addAlt(new Alt(new RegExp("[a-z]+")));

		Alt plus = new Alt(s, new Char('+'), s);
		plus.disallowAt(2, plus);
		s.addAlt(plus);
		
		Alt mul = new Alt(s, new Char('*'), s);
		mul.disallowAt(2, mul);
		mul.disallowAt(0, plus);
		mul.disallowAt(2, plus);
		s.addAlt(mul);
		
		Alt bracket = new Alt(new Char('('), s, new Char(')'));
		s.addAlt(bracket);

		NonTerminal jurgen = new NonTerminal("Jurgen");
		jurgen.addAlt(new Alt(s));
		
		String src = "x+y+z*c*(a+b)";
//		System.out.println("Src = " + src);
		GLL d = new GLL(jurgen);
		long before = System.currentTimeMillis();
		Node x = d.parse(src);
		long after = System.currentTimeMillis();
		
		
		System.out.println(x.toDot());
		writeDot("exp1.dot", x.toDot());
		writeDot("exp2.dot", Flattener.flatten(x).toDot());
		d.printStatistics();
		
		System.out.println("Time in s:" + (after - before)* 1000.0);
	}
	
	private static void expressionsDeep() {
		NonTerminal s = new NonTerminal("Exp");
		s.addAlt(new Alt(new RegExp("[a-z]+")));

		Alt plus = new Alt(s, new Char('+'), s);
		s.addAlt(plus);
		plus.leftAssociative();
		
		Alt mul = new Alt(s, new Char('*'), s);
		s.addAlt(mul);
		mul.leftAssociative();
		mul.strongerThan(plus);
		
		Alt alt = mul;
		for (int i = 0; i < 15; i++) {
			Alt newAlt = new Alt(s, new Char('/'), s);
			s.addAlt(newAlt);
			newAlt.leftAssociative();
			newAlt.strongerThan(alt);
			alt = newAlt;
		}

		
		Alt bracket = new Alt(new Char('('), s, new Char(')'));
		s.addAlt(bracket);

		NonTerminal jurgen = new NonTerminal("Jurgen");
		jurgen.addAlt(new Alt(s));
		
		String src = "x+y+z*c*(a+b)";
		src = "x";
		for (int i = 0; i < 30; i++) {
			src += "/x";
		}
//		System.out.println("Src = " + src);
		GLL d = new GLL(jurgen);
		long before = System.currentTimeMillis();
		Node x = d.parse(src);
		long after = System.currentTimeMillis();
		System.out.println(x.toDot());
		writeDot("exp1.dot", x.toDot());
//		writeDot("exp2.dot", Flattener.flatten(x).toDot());
		d.printStatistics();
		System.out.println("Time in s: " + (after - before) / 1000.0);
	}

	private static void empty() {
		NonTerminal s = new NonTerminal("Exp");
		s.addAlt(new Alt());
		GLL d = new GLL(s);
		Node t = d.parse("");
		System.out.println(t.toDot());
		d.printStatistics();
	}
	
	private static void iter() {
		NonTerminal s = new NonTerminal("Exp");
		NonTerminal x = new NonTerminal("X");
		Char xchar = new Char('x');
		x.addAlt(new Alt(xchar));
		s.addAlt(new Alt(new IterStar(x)));
		GLL d = new GLL(s);
		Node t = d.parse("xxx");
		System.out.println(t.toDot());
		System.out.println(t.toDot());
		writeDot("iter1.dot", t.toDot());
		writeDot("iter2.dot", Flattener.flatten(t).toDot());
		d.printStatistics();
	}
	
	private static void iterIter() {
		NonTerminal s = new NonTerminal("Exp");
		NonTerminal x = new NonTerminal("X");
		Char xchar = new Char('x');
		x.addAlt(new Alt(xchar));
		s.addAlt(new Alt(new IterStar(new IterStar(x))));
		GLL d = new GLL(s);
		Node t = d.parse("xxx");
		System.out.println(t.toDot());
		writeDot("iterIter1.dot", t.toDot());
		writeDot("iterIter2.dot", Flattener.flatten(t).toDot());
		d.printStatistics();
	}
	
	private static void cycle() {
		NonTerminal s = new NonTerminal("S");
		NonTerminal a = new NonTerminal("A");
		s.addAlt(new Alt(a));
		a.addAlt(new Alt(s));
		a.addAlt(new Alt(new Char('a')));
		
		GLL d = new GLL(s);
		Node t = d.parse("a");
		System.out.println(t.toDot());
		d.printStatistics();
		
	}
	
	private static void cycle2() {
		NonTerminal s = new NonTerminal("S");
		NonTerminal a = new NonTerminal("A");
		NonTerminal b = new NonTerminal("B");
		NonTerminal c = new NonTerminal("C");
		NonTerminal d = new NonTerminal("D");
		s.addAlt(new Alt(a, b, c));
		a.addAlt(new Alt());
		b.addAlt(new Alt(new Char('b')));
		b.addAlt(new Alt(d));
		c.addAlt(new Alt());
		d.addAlt(new Alt(s));

		
		GLL gll = new GLL(s);
		Node t = gll.parse("b");
		
		System.out.println(Flattener.flatten(t).toDot());
		writeDot("cycle1.dot", t.toDot());
		writeDot("cycle2.dot", Flattener.flatten(t).toDot());
		gll.printStatistics();
		
	}
	
	private static void horizAmb() {
		NonTerminal s = new NonTerminal("S");
		NonTerminal a = new NonTerminal("A");
		NonTerminal b = new NonTerminal("B");
		a.addAlt(new Alt(new Char('a'), new Char('x')));
		a.addAlt(new Alt(new Char('a')));
		b.addAlt(new Alt(new Char('b')));
		b.addAlt(new Alt(new Char('x'), new Char('b')));
		s.addAlt(new Alt(a, b, new Char('c')));
		GLL d = new GLL(s);
		Node t = d.parse("axbc");
		writeDot("horiz1.dot", t.toDot());
		writeDot("horiz2.dot", Flattener.flatten(t).toDot());
		System.out.println(t.toDot());
		d.printStatistics();
	}


	public static void writeDot(String fileName, String dot) {
		File f = new File(fileName);
		try {
			FileWriter w = new FileWriter(f);
			w.write(dot);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
