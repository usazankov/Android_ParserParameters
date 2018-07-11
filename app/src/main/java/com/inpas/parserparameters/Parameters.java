package com.inpas.parserparameters;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.inpas.model.*;
import com.inpas.libparseparams.IParserParameters;

public class Parameters {
	private static volatile Parameters instance;
	private IParserParameters parser;
	private CurrencyPreset cur;
	private PaymentSystemPreset paym;
	private CardProductPreset product;
	private SecurityKeyPreset keys;
	private AccountTypePreset account;
	private TemplatePreset templates;
	private TerminalProfilePreset profile;
	private UsersGroupPreset users;
	private PossessorPreset possessors;
	private AcquiringPreset acquiring;
	private ConnectionsServerPreset connections;
	private TerminalPreset term;
	private Map<File, Class<?>> fileClass;

	public void setParser(IParserParameters parser) {
		this.parser = parser;
	}
	
	public void setMapFileClass(Map<File, Class<?>> fileClass) {
		this.fileClass = fileClass;
	}
	
	public void readParameters() throws Exception{
	    if( fileClass == null || fileClass.isEmpty()){
            Log.d(MainActivity.logTag, "Не задана таблица File-ClassName");
            return;
        }
        if( parser == null){
            Log.d(MainActivity.logTag, "Не задан парсер параметров");
        }
		for (Map.Entry<File, Class<?>> entry : fileClass.entrySet()) {
		    File file = entry.getKey();
            Class<?> c = entry.getValue();
		    if(!file.exists()){
                Log.d(MainActivity.logTag, "Файла " + file.getAbsolutePath() + " не существует, объект класса " + c.getName() + " не будет создан");
		        continue;
            }
			byte[] data = new byte[(int)file.length()];
			try {
				BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
				buf.read(data, 0, data.length);
				buf.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
				continue;
			}catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		    if(c == CurrencyPreset.class) {
		    	cur = parser.parse(data, CurrencyPreset.class);
		    }else if(c == PaymentSystemPreset.class) {
		    	paym = parser.parse(data, PaymentSystemPreset.class);
		    }else if(c == CardProductPreset.class) {
		    	product = parser.parse(data, CardProductPreset.class);
		    }else if(c == SecurityKeyPreset.class) {
		    	keys = parser.parse(data, SecurityKeyPreset.class);
		    }else if(c == AccountTypePreset.class) {
		    	account = parser.parse(data, AccountTypePreset.class);
		    }else if(c == TemplatePreset.class) {
		    	templates = parser.parse(data, TemplatePreset.class);
		    }else if(c == TerminalProfilePreset.class) {
		    	profile = parser.parse(data, TerminalProfilePreset.class);
		    }else if(c == UsersGroupPreset.class) {
		    	users = parser.parse(data, UsersGroupPreset.class);
		    }else if(c == PossessorPreset.class) {
		    	possessors = parser.parse(data, PossessorPreset.class);
		    }else if(c == AcquiringPreset.class) {
		    	acquiring = parser.parse(data, AcquiringPreset.class);
		    }else if(c == ConnectionsServerPreset.class) {
		    	connections = parser.parse(data, ConnectionsServerPreset.class);
		    }else if(c == TerminalPreset.class) {
		    	term = parser.parse(data, TerminalPreset.class);
		    }else{
                Log.d(MainActivity.logTag,"Ошибка парсинга файла " + file.getName() + " - класс с именем " + c.getName() + " не обрабатывается");
            }
		}
	}
	
	public static Parameters get() {
		if (instance == null) {
			synchronized (Parameters.class) {
				if (instance == null) {
					instance = new Parameters();
				}
			}
		}
		return instance;
	}
	
	public Terminal getTerminal() {
	    if(term != null) return term.getTerminal();
	    return null;
	}
	
	public CurrencyPreset getCurrencyPreset() {
		return cur;
	}

	public PaymentSystemPreset getPaymentSystemPreset() {
		return paym;
	}

	public CardProductPreset getCardProductPreset() {
		return product;
	}

	public SecurityKeyPreset getSecurityKeyPreset() {
		return keys;
	}

	public AccountTypePreset getAccountPreset() {
		return account;
	}

	public TemplatePreset getTemplatesPreset() {
		return templates;
	}

	public TerminalProfilePreset getTerminalProfilePreset() {
		return profile;
	}
	
	public UsersGroupPreset getUsersGroupPreset() {return users; }

	public PossessorPreset getPossessorsPreset() {
		return possessors;
	}

	public AcquiringPreset getAcquiringPreset() {
		return acquiring;
	}
	
	public ConnectionsServerPreset getConnectionsServerPreset() {
		return connections;
	}
}
