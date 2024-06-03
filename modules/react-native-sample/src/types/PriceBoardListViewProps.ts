import {NativeSyntheticEvent, ViewStyle} from 'react-native';
import {Quote} from '..';
import {PropsWithChildren} from 'react';

export type PriceBoardListViewProps = {
  onPressHandle?: (item: Quote) => void;
  listHeaderComponent?: React.ReactNode | undefined;
  listFooterComponent?: React.ReactNode | undefined;
};
export type NativePriceBoardListViewProps = Omit<
  PriceBoardListViewProps,
  'onPressHandle'
> & {
  onPressHandle: (data: NativeSyntheticEvent<{item: any}>) => void;
  style?: ViewStyle;
} & PropsWithChildren;
