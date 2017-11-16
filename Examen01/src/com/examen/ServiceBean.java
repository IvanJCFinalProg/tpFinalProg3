package com.examen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ServiceBean implements Comparator<Bean>{
	public boolean addBean(List<Bean> liste, Bean bean) {
		return liste.add(bean);
	}
	public boolean removeBean(List<Bean> liste, Bean bean) {
		return liste.remove(bean);
	}
	public Bean getBeanById(List<Bean> liste, int id) {
		for(Bean bean : liste)
			if(bean.getIdBean() == id)
				return bean;
		return null;
	}
	
	public List<Bean> loadFromXML(String fileName){
		try {
			XStream stream = new XStream(new DomDriver());
			//stream.alias("com.examen.Bean", Bean.class);
			return (List<Bean>) stream.fromXML((new FileInputStream(fileName)));
		}catch(Exception e) {e.printStackTrace();}
		return null;
	}
	public Map<Integer, Bean> toMap(List<Bean> liste){
		Map<Integer, Bean> map = new HashMap<Integer, Bean>();
		for(Bean bean : liste)
			map.put(bean.getIdBean(), bean);
		return map;
	}
	public int getNbFemmeRicheCelibataire(List<Bean> liste) {
		int compteur = 0;
		for(Bean bean : liste)
			if(bean.getSexe().equals("feminin") && bean.getSalaire()>17652.00 && bean.getEtatCivil().equals("celibataire"))
				compteur++;
		return compteur;
	}
	public int augmenterSalaireRetraite(List<Bean> liste) {
		int compteur = 0;
		for(Bean bean : liste)
			if(bean.getStatut().equals("retraite")) {
				bean.setSalaire(bean.getSalaire()*1.0472);
				compteur++;
			}
		return compteur;
	}
	public int getNbTempsPartielEtEnCouple(List<Bean> liste) {
		int compteur = 0;
		for(Bean bean : liste)
			if(bean.getType().equals("tempsPartiel") && bean.getEtatCivil().equals("EnCouple"))
				compteur++;
		return compteur;
	}
	public boolean saveBeanToXml(List<Bean> liste) {
		List<Bean> listeHommes = new ArrayList<>();
		List<Bean> listeFemmes = new ArrayList<>();
		for(Bean bean : liste) 
			if(bean.getSexe().equals("masculin"))
				listeHommes.add(bean);
			else 
				listeFemmes.add(bean);
		try {
			XStream stream = new XStream(new DomDriver());
			stream.toXML(listeHommes, new FileOutputStream("BeanMasculin.xml"));
			stream.toXML(listeFemmes, new FileOutputStream("BeanFeminin.xml"));
			return ((new File("BeanMasculin.xml").exists()) && (new File("BeanFeminin.xml").exists()));
		}catch(Exception e) {}
		return false;
	}
	@Override
	public int compare(Bean o1, Bean o2) {
		return ((Double)o1.getSalaire()).compareTo(o2.getSalaire());
	}
}
