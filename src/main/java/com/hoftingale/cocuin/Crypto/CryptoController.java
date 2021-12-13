package com.hoftingale.cocuin.Crypto;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cryptos")
public class CryptoController {

    private final CryptoSetModel cryptoSetModel;
    public CryptoController(CryptoSetModel cryptoSetModel) {
        this.cryptoSetModel = cryptoSetModel;
    }
    @GetMapping
        public String getCurrencies(Model model){
        try {
            CryptoSetModel.refreshCurrencies();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("cryptos", CryptoSetModel.getAllCurrencies());
            return "cryptos";
        }
}

