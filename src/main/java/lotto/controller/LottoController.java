package lotto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

import lotto.domain.LottoResult;
import lotto.views.InputViews;
import lotto.views.OutputViews;
import lotto.domain.Lotto;
import lotto.domain.MakeLottos;
import lotto.domain.InputWinningNum;

public class LottoController {
    int purchaseAmount, purchaseNum, bonusNumInput;
    int reciveAmount = 0;
    private static List<List<Integer>> allLotto;
    private static Lotto lotto;

    public void run() {
        while (readPurchaseLotto()) {
        }
        OutputViews.endOfSection();

        writePurchaseLotto();
        OutputViews.endOfSection();

        while (readWinningNum()){
        }
        OutputViews.endOfSection();

        while (readBonusNum()) {
        }
        OutputViews.endOfSection();

        writeMatchesNumResult();
//        System.out.println(reciveAmount);
    }

    public boolean readPurchaseLotto() {
        String purchaseAmountString = InputViews.readPurchaseAmount();
        if (ReadPurchaseLottoModules.isPurchaseAmountInt(purchaseAmountString)) {
            return true;
        }

        purchaseAmount = Integer.parseInt(purchaseAmountString);
        if (ReadPurchaseLottoModules.isPurchaseAmountPositive(purchaseAmount)) {
            return true;
        }
        if (ReadPurchaseLottoModules.isPurchaseAmountDivisible1000(purchaseAmount)) {
            return true;
        }
        purchaseNum = purchaseAmount / 1000;
        return false;
    }

    public void writePurchaseLotto() {
        allLotto = MakeLottos.makeLotto(purchaseNum);
        OutputViews.numOfPurchaseLotto(purchaseNum);
        OutputViews.listOfPurchaseLotto(allLotto);
    }

    public boolean readWinningNum() {
        String winningNumsNotSplit = InputViews.readWinningNum();
        List<String> winningNums = Arrays.asList(winningNumsNotSplit.replace(" ", "").split(","));
        List<Integer> winningNumsInput = new ArrayList<>();
        for (String winningNum : winningNums) {
            if (ReadWinningNum.isWinningNumTypeOfInput(winningNum)) {
                return true;
            }
            winningNumsInput.add(Integer.parseInt(winningNum));
        }
        lotto = ReadWinningNum.makeWinningNumsInput(winningNumsInput);
        if (lotto == null) {
            return true;
        }
        return false;
    }

    public boolean readBonusNum() {
        String bonusNum = InputViews.readBonusNum();
        if (ReadWinningNum.isBonusNumInt(bonusNum)) {
            return true;
        }

        bonusNumInput = Integer.parseInt(bonusNum);
        if (ReadWinningNum.isBonusNumRange(bonusNumInput)) {
            return true;
        }
        if (lotto.bonusValidateBool(bonusNumInput)) {
            return true;
        }
        return false;
    }

    public void writeMatchesNumResult() {
        lotto.lottoResultSearch(allLotto, bonusNumInput);
        OutputViews.startWriteStat();
        for (LottoResult lottoResultName: LottoResult.values()) {
            String stsMess = lottoResultName.getMatchesMess();
            int matchNum = lottoResultName.getNumOfMatches();
            OutputViews.wrtieResultStatistic(stsMess, matchNum);

            reciveAmount += lottoResultName.getAmount() * matchNum;
//            System.out.println("reciveAmount : " + reciveAmount);
        }
        double per = (double) reciveAmount/purchaseAmount * 100;
//        System.out.println("purchaseAmount : " + purchaseAmount);
//        System.out.println("per : " + per);
        OutputViews.wrtieResultRate(per);
    }
}
