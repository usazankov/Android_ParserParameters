package com.inpas.parserparameters;
import java.io.File;
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
	//private UsersGroupPreset users;
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
		for (Map.Entry<File, Class<?>> entry : fileClass.entrySet()) {
		    File file = entry.getKey();
		    Class<?> c = entry.getValue();
		    byte[] data;
		    data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
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
		    //}else if(c == UsersGroupPreset.class) {
		    //	users = parser.parse(data, UsersGroupPreset.class);
		    }else if(c == PossessorPreset.class) {
		    	possessors = parser.parse(data, PossessorPreset.class);
		    }else if(c == AcquiringPreset.class) {
		    	acquiring = parser.parse(data, AcquiringPreset.class);
		    }else if(c == ConnectionsServerPreset.class) {
		    	connections = parser.parse(data, ConnectionsServerPreset.class);
		    }else if(c == TerminalPreset.class) {
		    	term = parser.parse(data, TerminalPreset.class);
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
		return term.getTerminal();
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
	
	//public UsersGroupPreset getUsersGroupPreset() {
	//	return users;
	//}

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
