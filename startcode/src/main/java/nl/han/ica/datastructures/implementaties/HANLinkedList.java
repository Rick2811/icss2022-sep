    package nl.han.ica.datastructures.implementaties;

    import nl.han.ica.datastructures.IHANLinkedList;

    public class HANLinkedList<T> implements IHANLinkedList<T> {

        private Node<T> head;
        private int size = 0;

        private static class Node<T> {
            T value;
            Node<T> next;
            Node(T value) { this.value = value; }
        }

        @Override
        public void addFirst(T value) {
            Node<T> newNode = new Node<>(value);
            newNode.next = head;
            head = newNode;
            size++;
        }

        @Override
        public void clear() {
            head = null;
            size = 0;
        }

        @Override
        public void insert(int index, T value) {
            if (index == 0) {
                addFirst(value);
                return;
            }
            Node<T> current = head;
            for (int i = 0; i < index - 1 && current != null; i++) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(value);
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }

        @Override
        public void delete(int pos) {
            if (pos == 0 && head != null) {
                head = head.next;
                size--;
                return;
            }
            Node<T> current = head;
            for (int i = 0; i < pos - 1 && current != null; i++) {
                current = current.next;
            }
            if (current != null && current.next != null) {
                current.next = current.next.next;
                size--;
            }
        }

        @Override
        public T get(int pos) {
            Node<T> current = head;
            for (int i = 0; i < pos && current != null; i++) {
                current = current.next;
            }
            return current == null ? null : current.value;
        }

        @Override
        public void removeFirst() {
            if (head != null) {
                head = head.next;
                size--;
            }
        }

        @Override
        public T getFirst() {
            return head == null ? null : head.value;
        }

        @Override
        public int getSize() {
            return size;
        }
    }
