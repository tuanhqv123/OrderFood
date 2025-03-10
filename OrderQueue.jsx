import { Eye, Minus, Plus } from 'lucide-react';
import { useState } from 'react';

interface OrderItem {
  id: string;
  status: 'ready' | 'cooking' | 'cancelled' | 'new';
  customer: string;
  datetime: string;
  table: string;
  items: number;
}

export const OrderQueue = () => {
  const [orders, setOrders] = useState<OrderItem[]>([
    {
      id: '#12D0B-3A',
      status: 'ready',
      customer: 'Anggito Dwi Pratama',
      datetime: '12-01-2025, 11:30 pm',
      table: 'Table 3B',
      items: 5
    },
    {
      id: '#12D0B-3A',
      status: 'cooking',
      customer: 'Dwi Lestari Salsabila',
      datetime: '12-01-2025, 09:24 pm',
      table: 'Table 2C',
      items: 8
    },
    {
      id: '#12D0B-3A',
      status: 'cancelled',
      customer: 'Devano Cahyo Anggara',
      datetime: '12-01-2025, 10:05 pm',
      table: 'Table 1D',
      items: 4
    },
    {
      id: '#1',
      status: 'new',
      customer: 'Anthony',
      datetime: '12-01-2025, 10:05 pm',
      table: 'Table 1D',
      items: 6
    }
  ]);

  const getStatusBadgeClass = (status: string) => {
    switch (status) {
      case 'ready': return 'status-badge status-badge-ready';
      case 'cooking': return 'status-badge status-badge-cooking';
      case 'cancelled': return 'status-badge status-badge-cancelled';
      default: return 'status-badge bg-gray-100 text-gray-800';
    }
  };

  const getStatusText = (status: string) => {
    switch (status) {
      case 'ready': return 'Ready to serve';
      case 'cooking': return 'On cooking';
      case 'cancelled': return 'Cancelled';
      case 'new': return 'New order';
      default: return status;
    }
  };

  return (
    <div className="mb-8">
      <div className="flex items-center justify-between mb-6">
        <h2 className="text-xl font-semibold">Order queues</h2>
        <div className="flex gap-2">
          <button className="flex items-center gap-1 text-gray-600 hover:text-gray-900">
            <Eye size={18} />
            <span className="text-sm">View All</span>
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        {orders.map((order, index) => (
          <div key={index} className="bg-white rounded-lg p-4 shadow-card border border-gray-100 animate-fade-in" style={{ animationDelay: `${index * 0.05}s` }}>
            <div className="flex items-center justify-between mb-3">
              <span className="text-sm font-medium text-gray-600">{order.id}</span>
              <span className={getStatusBadgeClass(order.status)}>
                {getStatusText(order.status)}
              </span>
            </div>
            
            <h3 className="font-medium mb-1">{order.customer}</h3>
            <p className="text-sm text-gray-500 mb-3">{order.datetime}</p>
            
            <div className="flex justify-between items-center">
              <div className="flex items-center gap-1">
                <span className="inline-block w-5 h-5 rounded-full bg-gray-100 flex items-center justify-center">
                  <span className="text-xs">{order.items}</span>
                </span>
                <span className="text-sm text-gray-600">items</span>
              </div>
              
              <div className="text-sm text-gray-600">{order.table}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default OrderQueue;
