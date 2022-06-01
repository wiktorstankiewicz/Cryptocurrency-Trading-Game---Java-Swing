package interfaces.pricePredictionStrategy;

import model.CurrencyModel;

import java.io.Serial;
import java.io.Serializable;

public class LongTermPrediction implements PricePredictor, Serializable {
    @Serial
    private static final long serialVersionUID = 1645813064584721941L;

    @Override
    public SugestedAction getSuggestedAction(CurrencyModel currencyModel) {
        double averagePrice = currencyModel.getAveragePrice();
        double suggestionTriggeringPercent = 0.2;

        if (currencyModel.getCryptoCurrency().getCurrentPrice() > averagePrice + suggestionTriggeringPercent * averagePrice) {
            return SugestedAction.SELL;
        }
        if (currencyModel.getCryptoCurrency().getCurrentPrice() < averagePrice - suggestionTriggeringPercent * averagePrice) {
            return SugestedAction.BUY;
        }
        return SugestedAction.NOT_DETERMINED;
    }
}
