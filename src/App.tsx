import React, {useEffect, useRef, useState} from 'react';
import {Button, SafeAreaView, StyleSheet, View} from 'react-native';
import {
  EndlessPriceBoardListView,
  EndlessQuote,
  PriceBoardListView,
  Quote,
} from 'react-native-sample';
import priceBoard from '../data/price-board.json';
import {setupTheming} from './theming';
import {faker} from '@faker-js/faker';
const priceBoardData = priceBoard.slice(0, 100);
const status = ['UP', 'DOWN', 'UNKNOWN'] as const;

// we need call this function to initialize the fakerjs
// i don't know why, but if we call faker functions when click button, the first times call will be slow(about 1s delay)
faker.finance.amount({min: 555, max: 5555, dec: 2}).toString();

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
  const endlessPriceBoardListRef = useRef<EndlessPriceBoardListView>(null);

  // func
  const handleUpdate = () => {
    clearInterval(timeInterval);
    timeInterval = setInterval(() => {
      const nextItem = faker.helpers.arrayElement(priceBoardData);

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
        percent: `${percent}%`,
        percentStatus,
      });
      endlessPriceBoardListRef.current?.updateItem({
        ...nextItem,
        tradePrice,
        tradePriceStatus,
        point,
        pointStatus,
        percent: `${percent}%`,
        percentStatus,
      });
    }, 1);
  };

  // effect
  useEffect(() => {
    setTimeout(() => {
      priceBoardListRef.current?.setData(priceBoardData as Array<Quote>);
      endlessPriceBoardListRef.current?.setData(
        priceBoardData as Array<EndlessQuote>,
      );
    }, 100);
  }, []);

  // render
  return (
    <SafeAreaView style={styles.root}>
      <Button title="Start Update" onPress={handleUpdate} />
      <View style={styles.endlessList}>
        <EndlessPriceBoardListView
          ref={endlessPriceBoardListRef}
          onPressHandle={e => {
            console.log({e});
          }}
        />
      </View>
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
  endlessList: {
    height: 100,
    width: '100%',
  },
});
