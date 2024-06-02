const fs = require('fs');

const {faker} = require('@faker-js/faker');

const status = ['UP', 'DOWN', 'UNKNOWN'];

const createSymbolName = () => {
  const preWork = [1, 2, 3]
    .map(() => faker.string.alpha().toUpperCase())
    .join('');
  const lastWork = [1, 2, 3]
    .map(() => faker.string.alpha().toUpperCase())
    .join('');
  return `${preWork}.${lastWork}`;
};

const createPriceBoardData = () => {
  const data = Array.from({length: 1000}, () => {
    const key = faker.string.uuid();
    const symbolName = createSymbolName();
    const companyName = faker.company.name();
    const tradePrice = faker.finance
      .amount({min: 555, max: 5555, dec: 2})
      .toString();
    const tradePriceStatus = faker.helpers.arrayElement(status);
    const point = faker.number
      .float({min: 100, max: 1000, fractionDigits: 2})
      .toString();
    const pointStatus = faker.helpers.arrayElement(status);
    const percent = faker.number
      .float({
        min: -100,
        max: 200,
        fractionDigits: 2,
      })
      .toString();
    const percentStatus = faker.helpers.arrayElement(status);
    return {
      key,
      symbolName,
      companyName,
      tradePrice,
      tradePriceStatus,
      point,
      pointStatus,
      percent,
      percentStatus,
    };
  });

  fs.writeFileSync('./data/price-board.json', JSON.stringify(data, null, 2));
};
(() => {
  createPriceBoardData();
})();
