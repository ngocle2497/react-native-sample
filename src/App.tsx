import React, {useEffect, useRef, useState} from 'react';
import {Button, SafeAreaView, StyleSheet} from 'react-native';
import {PriceBoardListView, Quote} from 'react-native-sample';
import priceBoard from '../data/price-board.json';
import {setupTheming} from './theming';
import {faker} from '@faker-js/faker';
const priceBoardData = priceBoard.slice(0, 10);
const status = ['UP', 'DOWN', 'UNKNOWN'] as const;

const AppWrapper = () => {
  // state
  const [loaded, setLoaded] = useState(false);

  // func
  const loadTheme = async () => {
    await setupTheming();
    setLoaded(true);
  };
  // effect
  useEffect(() => {
    loadTheme();
  }, []);

  // render
  if (loaded) {
    return <App />;
  }
  return null;
};
let timeInterval: any = null;
const App = () => {
  // state
  const priceBoardListRef = useRef<PriceBoardListView>(null);

  // func
  const handleUpdate = () => {
    clearInterval(timeInterval);
    timeInterval = setInterval(() => {
      const nextItem = faker.helpers.arrayElement(priceBoardData);
      console.log(nextItem.key);

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
      priceBoardListRef.current?.updateItem({
        ...nextItem,
        tradePrice,
        tradePriceStatus,
        point,
        pointStatus,
        percent,
        percentStatus,
      });
    }, 1);
  };

  // effect
  useEffect(() => {
    priceBoardListRef.current?.setData(priceBoardData as Array<Quote>);
  }, []);

  // render
  return (
    <SafeAreaView style={styles.root}>
      <Button title="Start Update" onPress={handleUpdate} />
      <PriceBoardListView
        ref={priceBoardListRef}
        onPressHandle={e => {
          console.log({e});
        }}
      />
    </SafeAreaView>
  );
};

export default AppWrapper;

const styles = StyleSheet.create({
  root: {
    flex: 1,
    backgroundColor: '#1D2939',
  },
});
