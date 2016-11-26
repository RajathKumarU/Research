package sentimentAnalysis;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class DependencyScanner {
	public static void main(String[] args) throws IOException {

		// Initialize the
		
		String line = new Scanner(System.in).nextLine();
		
		//System.out.println(tagged);
		LexicalizedParser lp = LexicalizedParser.loadModel(
				"edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz",
				"-maxLength", "80", "-retainTmpSubcategories");
		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		String[] sent = line.split(" ");
		Tree parse = lp.apply(Sentence.toWordList(sent));
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
		Collection<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
		System.out.println(tdl);
		Iterator itr = tdl.iterator();
		while( itr.hasNext() )
		{
			TypedDependency td = (TypedDependency)itr.next();
			IndexedWord iwgov = td.gov();
			IndexedWord iwdep = td.dep();
			GrammaticalRelation gr = td.reln();
			System.out.println(iwgov.word()+" "+iwdep.word()+" "+gr.getShortName());
		}
		
	}
}