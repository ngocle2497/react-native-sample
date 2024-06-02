export type ThemingType = {
  colors: Record<string, string>;
  textPresets: Record<
    string,
    {
      fontFamily: string;
      fontSize: number;
    }
  >;
};
