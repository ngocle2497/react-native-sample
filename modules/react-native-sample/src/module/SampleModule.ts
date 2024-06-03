import {NativeModules, ProcessedColorValue, processColor} from 'react-native';
import {ListViewTypeUnion} from '../types/ListViewType';
import {ThemingType} from '../types/ThemingType';

const {SampleModule} = NativeModules;

export const setDataList = (
  viewTag: number,
  type: ListViewTypeUnion,
  data: Array<object>,
) => {
  SampleModule.setDataList(viewTag, type, data);
};

export const updateItemList = (
  viewTag: number,
  type: ListViewTypeUnion,
  data: object,
) => {
  SampleModule.updateItemList(viewTag, type, data);
};

export const updateHeaderList = (viewId: number, headerViewId: number) => {
  return SampleModule.updateHeaderList(viewId, headerViewId);
};

export const updateFooterList = (viewId: number, footerViewId: number) => {
  return SampleModule.updateFooterList(viewId, footerViewId);
};

export const setTheme = (theme: ThemingType) => {
  const colors = Object.keys(theme.colors).reduce((prev, curr) => {
    prev[curr] = processColor(theme.colors[curr]);
    return prev;
  }, {} as Record<string, ProcessedColorValue | null | undefined>);
  SampleModule.setTheme({
    colors,
    textPresets: theme.textPresets,
    images: theme.images,
  });
};
