import Iconify from "../../mui_components/Iconify";

const getIcon = (name) => <Iconify icon={name} width={22} height={22} />;

const navConfig = [
  {
    title: "Update User",
    path: "/dashboard/user",
    icon: getIcon("eva:people-fill"),
  },
  {
    title: "Events",
    path: "/dashboard/products",
    icon: getIcon("eva:shopping-bag-fill"),
  },
  {
    title: "Forums",
    path: "/dashboard/blog",
    icon: getIcon("eva:file-text-fill"),
  },
];

export default navConfig;
