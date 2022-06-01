package interfaces.pricePredictionStrategy;

import model.CurrencyModel;

public interface PricePredictor {
    SugestedAction getSuggestedAction(CurrencyModel currencyModel);

}
