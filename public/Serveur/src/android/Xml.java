package android;

import java.io.File;
import java.util.List;
import java.util.Iterator;

import org.jdom2.*;
import org.jdom2.input.*;

public class Xml {
	private org.jdom2.Document doc;
	private Element racine;
	Element courant;
	private List<Element> epr;
	private Iterator<Element> i = epr.iterator(); 
	
	public void init(){
	}
	
	public List<Element> read(String parcour){
		SAXBuilder sxb = new SAXBuilder();
		try{
			this.doc = sxb.build(new File(parcour));
			this.racine = this.doc.getRootElement();
			this.epr = this.racine.getChildren("epreuve");
			this.courant = (Element) i.next();
		}catch(Exception e){};
		return this.epr;
	}
	
	public boolean nex(){
		if(this.i.hasNext()){
			this.courant = (Element)this.i.next();
			return true;
		}else{
			return false;
		}
	}
	
	public String getCoordoner(){
		return "" + this.courant.getChild("x").getText() + " # " + this.courant.getChild("y").getText();
	}
}