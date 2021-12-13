package com.hoftingale.cocuin.Crypto;

import com.hoftingale.cocuin.Utils.Requester;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@Service
public class CryptoSetModel {
    private static ArrayList<Currency> currencies;

    private static class Currency {
        private final String curName;
        private final String curSymbol;
        private final double curPrice;
        private final double curMarketCap;
        private final double changeRatio;
        private final double volume;
        private final double supply;

        public Currency(String curName, String curSymbol, String curPrice,
                        String curMarketCap, String changeRatio, String volume, String supply) {
            this.curName = curName;
            this.curSymbol = curSymbol;
            this.curPrice = Double.parseDouble(curPrice);
            this.curMarketCap = Double.parseDouble(curMarketCap);
            this.changeRatio = Double.parseDouble(changeRatio);
            this.volume = Double.parseDouble(volume);
            this.supply = Double.parseDouble(supply);

        }

        public static Currency getEmptyInstance(){
            return new Currency("null", "null", "0", "0",
                                    "0","0","0");
            }
        public HashMap<String, Object> getCurrencyData(){
            HashMap<String, Object> currencyData = new HashMap<>();
            currencyData.put("name", curName);
            currencyData.put("symbol", curSymbol);
            currencyData.put("price", curPrice);
            currencyData.put("marketcap", curMarketCap);
            currencyData.put("changeratio", changeRatio);
            currencyData.put("volume", volume);
            currencyData.put("supply", supply);
            return currencyData;
        }
    }

    public CryptoSetModel getInstance(){
        return this;
    }
    public static void refreshCurrencies() throws ParseException {
        Requester requester = new Requester("http://api.coincap.io/v2/assets");

        String apiData;
        JSONObject jsonData;
        JSONParser parser = new JSONParser();
        try {
            apiData = requester.getApiData();
            jsonData = (JSONObject) parser.parse(apiData);
        } catch(Exception e){
            currencies = new ArrayList<>();
            currencies.add(Currency.getEmptyInstance());
            return;
        }

        JSONArray currenciesData = (JSONArray) jsonData.get("data");
        currencies = new ArrayList<>();
        Iterator it = currenciesData.iterator();

        while(it.hasNext()){
            JSONObject temp = (JSONObject) parser.parse(it.next().toString());
            currencies.add(new Currency(temp.get("name").toString(),
                                        temp.get("symbol").toString(),
                                        temp.get("priceUsd").toString(),
                                        temp.get("marketCapUsd").toString(),
                                        temp.get("changePercent24Hr").toString(),
                                        temp.get("volumeUsd24Hr").toString(),
                                        temp.get("supply").toString()));
        }
    }

    public static ArrayList<HashMap<String, Object>> getAllCurrencies(){
        ArrayList<HashMap<String, Object>> allCurrencies = new ArrayList<>();
        for(Currency c : currencies){
            allCurrencies.add(c.getCurrencyData());
        }
        return allCurrencies;
    }

}
