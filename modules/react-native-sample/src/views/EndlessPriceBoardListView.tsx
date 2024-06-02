import React from 'react';
import {NativeSyntheticEvent, StyleSheet} from 'react-native';
import BaseListView from '../common/BaseListView';
import {NativeEndlessPriceBoardListView} from '../specs/SampleNativeComponents';
import {EndlessPriceBoardListViewProps} from '../types/EndlessPriceBoardListViewProps';
import {EndlessQuote} from '../types/EndlessQuote';
import {ListViewType} from '../types/ListViewType';

export class EndlessPriceBoardListView extends BaseListView<
  EndlessQuote,
  EndlessPriceBoardListViewProps
> {
  // ===== public functions ===== \\

  setData = (data: Array<EndlessQuote>) => {
    this._setData(data, ListViewType.ENDLESS);
  };

  updateItem = (data: EndlessQuote) => {
    this._updateItem(data, ListViewType.ENDLESS);
  };

  // ===== private functions ===== \\

  private onPressHandle = (
    data: NativeSyntheticEvent<{item: EndlessQuote}>,
  ) => {
    this.props.onPressHandle?.(data.nativeEvent.item);
  };

  // render
  render() {
    return (
      <NativeEndlessPriceBoardListView
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
