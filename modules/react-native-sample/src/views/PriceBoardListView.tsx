import React from 'react';
import BaseListView from '../common/BaseListView';
import {
  NativeFooterWrapperView,
  NativeHeaderWrapperView,
  NativePriceBoardListView,
} from '../specs/SampleNativeComponents';
import {ListViewType} from '../types/ListViewType';
import {Quote} from '../types/Quote';
import {NativeSyntheticEvent, Platform, StyleSheet, View} from 'react-native';
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
    if (Platform.OS === 'ios') {
      return (
        <NativePriceBoardListView
          // @ts-ignore
          ref={this.listHandleRef}
          onPressHandle={this.onPressHandle}
          style={styles.list}>
          <NativeHeaderWrapperView
            // @ts-ignore
            ref={this.headerRef}
            onLayout={this.onLayoutHeader}>
            {this.props.listHeaderComponent}
          </NativeHeaderWrapperView>
          <NativeFooterWrapperView
            // @ts-ignore
            ref={this.footerRef}
            onLayout={this.onLayoutFooter}>
            {this.props.listFooterComponent}
          </NativeFooterWrapperView>
        </NativePriceBoardListView>
      );
    }
    return (
      <NativePriceBoardListView
        // @ts-ignore
        ref={this.listHandleRef}
        style={styles.list}
        onPressHandle={this.onPressHandle}>
        <View style={styles.hiddenWrapperView}>
          <NativeHeaderWrapperView
            // @ts-ignore
            ref={this.headerRef}
            onLayout={this.onLayoutHeader}>
            <View style={styles.childWrapper}>
              {this.props.listHeaderComponent}
            </View>
          </NativeHeaderWrapperView>
        </View>
        <View style={styles.hiddenWrapperView}>
          <NativeFooterWrapperView
            // @ts-ignore
            ref={this.footerRef}
            onLayout={this.onLayoutFooter}>
            <View style={styles.childWrapper}>
              {this.props.listFooterComponent}
            </View>
          </NativeFooterWrapperView>
        </View>
      </NativePriceBoardListView>
    );
  }
}
const styles = StyleSheet.create({
  childWrapper: {
    width: '100%',
    backgroundColor: 'rgba(0,0,0,0)',
  },
  list: {
    flex: 1,
  },
  hiddenWrapperView: {
    position: 'absolute',
    width: '100%',
    backgroundColor: 'rgba(0,0,0,.01)',
  },
});
