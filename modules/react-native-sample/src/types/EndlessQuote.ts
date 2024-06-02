import {Quote} from '..';

export type EndlessQuote = Omit<Quote, 'companyName'>;
