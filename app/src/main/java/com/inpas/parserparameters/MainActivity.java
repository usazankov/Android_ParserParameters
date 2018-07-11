package com.inpas.parserparameters;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.inpas.libparseparams.TLVParserParameters;
import com.inpas.model.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String logTag = "params";
    private Map<File, Class<?>> map = new HashMap<File, Class<?>>();
    private void setFilesAndClass(){
        File rootDir = Environment.getExternalStorageDirectory();
        File path = new File(rootDir,"tlv/");
        map.put(new File(path,"Currency.pst"), CurrencyPreset.class);
        map.put(new File(path,"Paysys.pst"), PaymentSystemPreset.class);
        map.put(new File(path,"Card.pst"), CardProductPreset.class);
        map.put(new File(path,"Keys.pst"), SecurityKeyPreset.class);
        map.put(new File(path,"Account.pst"), AccountTypePreset.class);
        map.put(new File(path,"Template.pst"), TemplatePreset.class);
        map.put(new File(path,"Profile.pst"), TerminalProfilePreset.class);
        map.put(new File(path,"UsersGroups.pst"), UsersGroupPreset.class);
        map.put(new File(path,"Possessor.pst"), PossessorPreset.class);
        map.put(new File(path,"Acquiring.pst"), AcquiringPreset.class);
        map.put(new File(path,"Connections.pst"), ConnectionsServerPreset.class);
        map.put(new File(path,"Terminal.pst"), TerminalPreset.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFilesAndClass();
        TLVParserParameters parser = new TLVParserParameters();
        parser.setMapRootTagTlvToClassName(TagsTLV.tlvFieldsRoot);
        parser.setMapTagTlvToClassName(TagsTLV.tlvFields);
        Parameters.get().setParser(parser);
        Parameters.get().setMapFileClass(map);
        long start = System.currentTimeMillis();
        try {
            Parameters.get().readParameters();
        }catch (Exception ex){
            Log.d(logTag, ex.getMessage());
            ex.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println();
        Log.d(logTag, "Время разбора параметров:" + (double)(end - start) / 1000 + " сек.");
        Terminal term = Parameters.get().getTerminal();
        CurrencyPreset cur = Parameters.get().getCurrencyPreset();
        PaymentSystemPreset paym = Parameters.get().getPaymentSystemPreset();
        CardProductPreset card = Parameters.get().getCardProductPreset();
        SecurityKeyPreset keys = Parameters.get().getSecurityKeyPreset();
        AccountTypePreset account = Parameters.get().getAccountPreset();
        TemplatePreset template = Parameters.get().getTemplatesPreset();
        TerminalProfilePreset prof = Parameters.get().getTerminalProfilePreset();
        UsersGroupPreset users = Parameters.get().getUsersGroupPreset();
        PossessorPreset possessor = Parameters.get().getPossessorsPreset();
        AcquiringPreset acquring = Parameters.get().getAcquiringPreset();
        ConnectionsServerPreset connections = Parameters.get().getConnectionsServerPreset();
        Log.d(logTag,term.getAddress());
    }
}
