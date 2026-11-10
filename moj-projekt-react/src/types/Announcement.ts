export interface FilterOption {
  id: number | string;
  name: string;
  filter?: { name: string } | null;
}

export interface Announcement {
  id: number | string;
  title?: string;
  content?: string;
  description?: string;
  text?: string;
  price?: number | null;
  filterOptions?: FilterOption[] | null;
}