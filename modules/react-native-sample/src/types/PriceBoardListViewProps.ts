import {NativeSyntheticEvent, ViewStyle} from 'react-native';
import {Quote} from '..';

export type PriceBoardListViewProps = {
  onPressHandle?: (item: Quote) => void;
};
export type NativePriceBoardListViewProps = Omit<
  PriceBoardListViewProps,
  'onPressHandle'
> & {
  onPressHandle: (data: NativeSyntheticEvent<{item: any}>) => void;
  style?: ViewStyle;
};
