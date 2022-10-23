export  interface ProductFilter{
  keyword?: string;
  minPrice?: number;
  maxPrice?: number;
  categoryIdList: number[];
}
