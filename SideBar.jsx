import { useState } from "react";
import {
  LayoutDashboard,
  ShoppingCart,
  Package,
  Percent,
  Table,
  Users,
  List,
  BarChart2,
  Settings,
  HelpCircle,
  ChevronDown,
  LogOut,
} from "lucide-react";

interface SidebarProps {
  activeItem?: string;
}

export const Sidebar = ({ activeItem = "orders" }: SidebarProps) => {
  const [currentRestaurant, setCurrentRestaurant] = useState({
    name: "Bounty Catch Branch 1",
    location: "Indah Kapuk beach, Jakarta",
  });

  const [accounts, setAccounts] = useState([
    {
      id: 1,
      name: "Antonio Erlangga",
      email: "antonioer@gmail.com",
      active: true,
    },
    {
      id: 2,
      name: "Sherlina Putri",
      email: "sherlinaputri@gmail.com",
      active: false,
    },
  ]);

  const [showAccounts, setShowAccounts] = useState(false);

  const menuItems = [
    { id: "dashboard", label: "Dashboard", icon: LayoutDashboard },
    { id: "orders", label: "Orders", icon: ShoppingCart },
    { id: "inventory", label: "Inventory", icon: Package, badge: false },
    { id: "discounts", label: "Discounts", icon: Percent, badge: 8 },
    { id: "table", label: "Ordering table", icon: Table },
    { id: "customers", label: "Customers", icon: Users },
    { id: "lists", label: "Order lists", icon: List },
    { id: "analysis", label: "Analysis", icon: BarChart2 },
    { id: "settings", label: "Settings", icon: Settings },
    { id: "help", label: "Help center", icon: HelpCircle },
  ];

  return (
    <div className="w-64 h-full bg-white border-r border-gray-200 flex flex-col">
      <div className="p-4 flex items-center space-x-2 border-b border-gray-100">
        <div className="w-10 h-10 bg-foodpoint-primary rounded-lg flex items-center justify-center">
          <Package className="text-white w-5 h-5" />
        </div>
        <span className="font-semibold text-lg">FoodPoint</span>
      </div>

      <div className="p-4 border-b border-gray-100">
        <div className="text-sm text-gray-500 mb-1">Current restaurant</div>
        <div className="flex items-center justify-between">
          <div>
            <div className="font-medium">{currentRestaurant.name}</div>
            <div className="text-xs text-gray-500">
              {currentRestaurant.location}
            </div>
          </div>
          <button className="btn-icon text-gray-400 hover:text-gray-600">
            <ChevronDown size={18} />
          </button>
        </div>
      </div>

      <div className="flex-1 overflow-y-auto py-2">
        {menuItems.map((item) => (
          <a
            key={item.id}
            href="#"
            className={`sidebar-link ${activeItem === item.id ? "active" : ""}`}
          >
            <item.icon size={20} />
            <span>{item.label}</span>
            {item.badge && (
              <span className="ml-auto bg-gray-100 text-xs font-medium px-2 py-0.5 rounded-full">
                {item.badge}
              </span>
            )}
          </a>
        ))}
      </div>

      <div className="p-4 border-t border-gray-100">
        <button
          className="w-full text-left mb-2"
          onClick={() => setShowAccounts(!showAccounts)}
        >
          <div className="text-sm text-gray-500 mb-1">Switch account</div>
        </button>

        {showAccounts && (
          <div className="bg-white rounded-md shadow-elevated border border-gray-100 mb-3 animate-fade-in">
            {accounts.map((account) => (
              <div
                key={account.id}
                className={`p-2 flex items-center gap-2 hover:bg-gray-50 ${
                  account.active ? "bg-gray-50" : ""
                }`}
              >
                <div className="w-8 h-8 rounded-full bg-gray-200 flex-shrink-0 overflow-hidden">
                  {/* Avatar placeholder */}
                </div>
                <div className="overflow-hidden flex-1">
                  <div className="font-medium text-sm truncate">
                    {account.name}
                  </div>
                  <div className="text-xs text-gray-500 truncate">
                    {account.email}
                  </div>
                </div>
                {account.active && (
                  <div className="w-5 h-5 rounded-full bg-green-100 flex items-center justify-center">
                    <div className="w-3 h-3 rounded-full bg-green-500"></div>
                  </div>
                )}
              </div>
            ))}

            <div className="border-t border-gray-100 p-2">
              <button className="w-full text-left py-2 px-2 text-sm font-medium text-foodpoint-primary flex items-center justify-center gap-1 hover:bg-gray-50 rounded">
                <span>+</span> Add account
              </button>
            </div>

            <div className="border-t border-gray-100 p-2">
              <button className="w-full text-left py-2 px-2 text-sm font-medium text-red-500 flex items-center justify-center gap-1 hover:bg-gray-50 rounded">
                <LogOut size={14} /> Sign out
              </button>
            </div>
          </div>
        )}

        <div className="flex items-center gap-2 p-2">
          <div className="w-8 h-8 rounded-full bg-gray-200 flex-shrink-0"></div>
          <div className="overflow-hidden flex-1">
            <div className="font-medium text-sm truncate">
              {accounts[0].name}
            </div>
            <div className="text-xs text-gray-500 truncate">
              {accounts[0].email}
            </div>
          </div>
          <button className="btn-icon text-gray-400 hover:text-gray-600">
            <ChevronDown size={16} />
          </button>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
