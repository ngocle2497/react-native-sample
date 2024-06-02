export type ThemingType = {
  colors: Record<string, string>;
  images: Record<string, string>;
  textPresets: Record<
    string,
    {
      fontFamily: string;
      fontSize: number;
    }
  >;
};
