struct Quote {
    let key                 : String
    let symbolName          : String
    let companyName         : String
    let tradePrice          : String
    let tradePriceStatus    : QuoteBaseStatus
    let pointStatus         : QuoteBaseStatus
    let percentStatus       : QuoteBaseStatus
    let point               : String
    let percent         :    String
    
    
    static func dic2Quote(dic: NSDictionary) -> Quote {
        let key                 = dic["key"]                as? String  ?? ""
        let symbolName          = dic["symbolName"]         as? String  ?? ""
        let companyName         = dic["companyName"]        as? String  ?? ""
        let tradePrice          = dic["tradePrice"]         as? String  ?? ""
        let tradePriceStatus    = dic["tradePriceStatus"]   as? String  ?? ""
        let pointStatus         = dic["pointStatus"]        as? String  ?? ""
        let percentStatus       = dic["percentStatus"]      as? String  ?? ""
        let point               = dic["point"]              as? String  ?? ""
        let percent             = dic["percent"]            as? String  ?? ""
        
        return Quote(key: key,
                     symbolName         : symbolName,
                     companyName        : companyName,
                     tradePrice         : tradePrice,
                     tradePriceStatus   : QuoteBaseStatus.fromString(value: tradePriceStatus),
                     pointStatus        : QuoteBaseStatus.fromString(value: pointStatus),
                     percentStatus      : QuoteBaseStatus.fromString(value: percentStatus),
                     point              : point,
                     percent            : percent
        )
    }
    
    static func arrayDicToArrayQuote(arr: Array<NSDictionary>) -> Array<Quote> {
        return arr.map(Quote.dic2Quote)
    }
}

extension Quote {
    func toDic() -> [String: Any] {
        return [
            "key"               : key,
            "symbolName"        : symbolName,
            "companyName"       : companyName,
            "tradePrice"        : tradePrice,
            "tradePriceStatus"  : tradePriceStatus.rawValue,
            "pointStatus"       : pointStatus.rawValue,
            "percentStatus"     : percentStatus.rawValue,
            "point"             : point,
            "percent"           : percent
        ]
    }
}
