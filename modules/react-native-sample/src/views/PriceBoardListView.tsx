import React from 'react';
import BaseListView from '../common/BaseListView';
import {NativePriceBoardListView} from '../specs/SampleNativeComponents';
import {ListViewType} from '../types/ListViewType';
import {Quote} from '../types/Quote';
import {NativeSyntheticEvent, StyleSheet} from 'react-native';
import {PriceBoardListViewProps} from '../types/PriceBoardListViewProps';

export class PriceBoardListView extends BaseListView<
  Quote,
  PriceBoardListViewProps
> {
  // ===== public functions ===== \\

  setData = (data: Array<Quote>) => {
    this._setData(data, ListViewType.PRICE_BOARD);
  };

  updateItem = (data: Quote) => {
    this._updateItem(data, ListViewType.PRICE_BOARD);
  };

  // ===== private functions ===== \\

  private onPressHandle = (data: NativeSyntheticEvent<{item: Quote}>) => {
    this.props.onPressHandle?.(data.nativeEvent.item);
  };

  // render
  render() {
    return (
      <NativePriceBoardListView
        // @ts-ignore
        ref={this.listHandleRef}
        style={styles.list}
        onPressHandle={this.onPressHandle}
      />
    );
  }
}
const styles = StyleSheet.create({
  list: {
    flex: 1,
  },
});
