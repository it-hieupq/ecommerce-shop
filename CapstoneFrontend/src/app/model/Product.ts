import {Image} from "./Image";

export interface Product{
  productId: number;
  name: string;
  description: string;
  price: number;
  inStock: number;
  active: boolean;
  categoryIdList: number[];
  images: Image[]
}
