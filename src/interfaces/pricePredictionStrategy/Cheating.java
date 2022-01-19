package interfaces.pricePredictionStrategy;

import model.CurrencyModel;
import utilities.CryptoCurrency;

import java.io.Serial;
import java.io.Serializable;

public class Cheating implements PricePredictor, Serializable {
    @Serial
    private static final long serialVersionUID = 3733669932304612773L;

    @Override
    public SugestedAction getSuggestedAction(CurrencyModel currencyModel) {
        CryptoCurrency.State state = currencyModel.getCryptoCurrency().getState();

        switch(state){
            case BEAR_MODE -> {
                return SugestedAction.SELL;
            }
            case BULL_MODE -> {
                return SugestedAction.BUY;
            }
            case STAGNATION_MODE -> {
                return SugestedAction.NOT_DETERMINED;
            }
            default -> throw new IllegalStateException();
        }
    }
}
