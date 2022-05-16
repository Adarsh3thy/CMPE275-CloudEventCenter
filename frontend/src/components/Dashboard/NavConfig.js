import Iconify from "../../mui_components/Iconify";

const getIcon = (name) => <Iconify icon={name} width={22} height={22} />;

const navConfig = [
  {
    title: "Update User",
    path: "/update-user",
    icon: getIcon("eva:people-fill"),
  },
  {
    title: "Events",
    path: "/events",
    icon: getIcon("eva:shopping-bag-fill"),
  },
  {
    title: "Registrations",
    path: "/registrations",
    icon: getIcon("eva:file-text-fill"),
  },
  {
    title: "My Events",
    path: "/myevents",
    icon: getIcon("eva:file-text-fill"),
  },
];

export default navConfig;
