package lotto.domain;

import java.util.List;
import java.util.HashMap;

public enum LottoResult {
    matches3(3, "3개 일치 (5,000원) - ", 5000, 0),
    matches4(4, "4개 일치 (50,000원) - ", 50000, 0),
    matches5(5, "5개 일치 (1,500,000원) - ", 1500000,  0),
    matches6(6, "6개 일치 (2,000,000,000원) - ", 2000000000, 0),
    matches5_bonus(5, "5개 일치, 보너스 볼 일치 (30,000,000원) - ", 30000000, 0)
    ;

    // 문자열을 저장할 필드
    private String matchesMess;
    private int matchesN, amount, numOfMatches;

    LottoResult(int matchesN, String matchesMess, int amount, int numOfMatches) {
        this.matchesN = matchesN;
        this.matchesMess = matchesMess;
        this.amount = amount;
        this.numOfMatches = numOfMatches;
    }

    public HashMap<String, Integer> getMatchesMessAndNum() {
        HashMap<String, Integer> messAndNum = new HashMap<>();
        messAndNum.put(matchesMess, numOfMatches);
        return messAndNum;
    }

    public int getAmount() {
        return amount;
    }

    public static void countResult(List<List<Integer>> allLotto, List<Integer> numbers, int bonusNum) {
        LottoResult[] lottoResultValues = LottoResult.values();
        for (List<Integer> eachLotto:allLotto) {
            int cntCorrNum = eachCount(eachLotto, numbers);
            if (cntCorrNum < 3) {
                continue;
            }
            if (cntCorrNum == 4) {
                cntCorrNum += isBonusNumInLotto(bonusNum, numbers);
            }
            LottoResult lottoResultSaveName = lottoResultValues[cntCorrNum - 3];
            lottoResultSaveName.numOfMatches += 1;
        }
    }

    public static int eachCount(List<Integer> eachLotto, List<Integer> numbers) {
        int winnerCnt = 0;
        for (int eachNum: eachLotto) {
            winnerCnt += isInLotto(eachNum, numbers);
        }
        return winnerCnt;
    }

    public static int isInLotto(int eachNum, List<Integer> numbers) {
        if (numbers.contains(eachNum)) {
            return 1;
        }
        return 0;
    }

    public static int isBonusNumInLotto(int bonusNum, List<Integer> numbers) {
        if (isInLotto(bonusNum, numbers) == 1) {
            return 2;
        };
        return 0;
    }

}
