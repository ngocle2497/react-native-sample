export type QuoteBaseStatus = 'UP' | 'DOWN' | 'UNKNOWN';

export type Quote = {
  key: string;
  symbolName: string;
  companyName: string;
  tradePrice: string;
  tradePriceStatus: QuoteBaseStatus;
  point: string;
  pointStatus: QuoteBaseStatus;
  percent: string;
  percentStatus: QuoteBaseStatus;
};
