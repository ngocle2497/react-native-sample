import {NativeSyntheticEvent, ViewStyle} from 'react-native';
import {EndlessQuote} from './EndlessQuote';

export type EndlessPriceBoardListViewProps = {
  onPressHandle?: (item: EndlessQuote) => void;
};
export type NativeEndlessPriceBoardListViewProps = Omit<
  EndlessPriceBoardListViewProps,
  'onPressHandle'
> & {
  onPressHandle: (data: NativeSyntheticEvent<{item: any}>) => void;
  style?: ViewStyle;
};
