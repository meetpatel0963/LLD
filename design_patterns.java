// VISITOR PATTERN

interface ShapeVisitor {
	void visit(Circle circle); 
	void visit(Rectangle rectangle); 
}

class AreaVisitor implements ShapeVisitor {
	@Override
	void visit(Circle circle) {
		//..
	}
	@Override
	void visit(Rectangle rectangle) {
		//..
	}
}

class PerimeterVisitor implements ShapeVisitor {
	@Override
	void visit(Circle circle) {
		//..
	}
	@Override
	void visit(Rectangle rectangle) {
		//..
	}
}

interface Shape {
	void accept(ShapeVisitor visitor); 
}

class Circle implements Shape {
	private double radius; 
	
	//constructor, getters, setters
	
	@Override
	void accept(ShapeVisitor visitor) {
		visitor.visit(this); 
	}
}

class Rectangle implements Shape {
	private double height; 
	private double width; 
	
	//constructor, getters, setters
	
	@Override
	void accept(ShapeVisitor visitor) {
		visitor.visit(this); 
	}
}

class ShapeCollection {
	private List<Shape> shapes = new ArrayList<>(); 
	
	public void addShape(Shape shape) {
		shapes.add(shape); 
	}
	
	public void processShape(ShapeVisitor visitor) {
		for(Shape shape : shapes) {
			shape.accept(visitor); 
		}
	}
}


// MEDIATOR PATTERN

interface ATCMediator {
	void sendMessage(String message, Plane plane); 
}

class ATCControl implements ATCMediator {
	private List<Plane> planes; 

	public ATCControl() {
		this.planes = new ArrayList<>(); 
	}

	public void registerPlane(Plane plane) {
		plenes.add(plane); 
	}

	@Override
	public void sendMessage(String message, Plane plane) {
		for(Plane p : planes) {
			if(p != plane) {
				p.receiveMessage(message); 
			}
		}
	}
}

interface Plane {
	void sendMessage(String message); 
	void receiveMessage(String message); 
}

class Airbus implements Plane {
	private ATCMediator mediator; 

	public Airbus(ATCMediator mediator) {
		this.mediator = mediator; 
	}

	@Override
	public void sendMessage(String message) {
		mediator.sendMessage(message, this); 
	}

	@Override
	public void receiveMessage(String message) {
		// do something .... 
	}
}

class Boeing implements Plane {
	private ATCMediator mediator; 

	public Boeing(ATCMediator mediator) {
		this.mediator = mediator; 
	}

	@Override
	public void sendMessage(String message) {
		mediator.sendMessage(message, this); 
	}

	@Override
	public void receiveMessage(String message) {
		// do something .... 
	}
}


// MEMENTO PATTERN 

class EditorMemento {
	private String content; 

	// constructor, getters, setters
}

class TextEditor {
	private String content; 
	// other fields

	// constructor, getters, setters

	public EditorMemento save() {
		return new EditorMemento(content); 
	}

	public void restore(EditorMemento memento) {
		content = memento.getContent(); 
	}
}

class History {
	private Stack<EditorMemento> mementos = new Stack<>(); 

	public void save(EditorMemento memento) {
		mementos.push(memento); 
	}

	public EditorMemento undo() {
		if(!mementos.isEmpty()) {
			return mementos.pop(); 
		}
		return null; 
	}
}

public class Main {
	public static void main(String[] args) {
		TextEditor editor = new TextEditor();
        History history = new History();

        // Type some content
        editor.setContent("Hello World");

        // Save the content to history (memento)
        history.save(editor.save());

        // Type some more content
        editor.setContent("Design Patterns are awesome!");

        // Save again
        history.save(editor.save());

        // Print current content
        System.out.println("Current content: " + editor.getContent());

        // Undo to previous state
        editor.restore(history.undo());

        // Print content after undo
        System.out.println("Content after undo: " + editor.getContent());
	}
}


// ITERATOR PATTERN

iterator Iterator<T> {
	boolean hasNext(); 
	T next(); 
}

iterface Aggregate<T> {
	Iterator<T> createIterator(); 
}

class Book {
	private String title; 

	// constructor, getters, setters
}

class BookCollection implements Aggregate<Book> {
	private List<Book> books; 

	// constructor

	public void addBook(Book book) {
		books.add(book); 
	}

	@Override
	public Iterator<Book> createIterator() {
		return new BookIterator(books); 
	}
}

class BookIterator implements Iterator<Book> {
	private List<Book> books; 
	private int position; 

	public BookIterator(List<Book> books) {
		this.books = books; 
		position = 0; 
	}

	@Override
	public boolean hasNext() {
		return position < books.size(); 
	}

	@Override
	public Book next() {
		if(!hasNext()) {
			throw new NoSuchElementException(); 
		}
		return books.get(position++); 
	}
}


// COMMAND PATTERN

// Command interface
interface Command {
    void execute();
}

// Receiver
class Light {
    private int brightness;

    public void turnOn() {
        System.out.println("Light is turned on");
        brightness = 100;
    }

    public void turnOff() {
        System.out.println("Light is turned off");
        brightness = 0;
    }

    public void dim(int level) {
        brightness -= level;
        System.out.println("Light brightness is set to " + brightness);
    }

    public void undo() {
        System.out.println("Undo last command");
    }
}

// Concrete Commands
class TurnOnCommand implements Command {
    private Light light;

    public TurnOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }
}

class TurnOffCommand implements Command {
    private Light light;

    public TurnOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }
}

class DimCommand implements Command {
    private Light light;
    private int level;

    public DimCommand(Light light, int level) {
        this.light = light;
        this.level = level;
    }

    @Override
    public void execute() {
        light.dim(level);
    }
}

class UndoCommand implements Command {
    private Light light;

    public UndoCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.undo();
    }
}

// Invoker
class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        command.execute();
    }
}

public class Main {
    public static void main(String[] args) {
        Light light = new Light();
        Command turnOnCommand = new TurnOnCommand(light);
        Command turnOffCommand = new TurnOffCommand(light);
        Command dimCommand = new DimCommand(light, 30);
        Command undoCommand = new UndoCommand(light);

        RemoteControl remoteControl = new RemoteControl();

        // Turning on the light
        remoteControl.setCommand(turnOnCommand);
        remoteControl.pressButton();

        // Dimming the light
        remoteControl.setCommand(dimCommand);
        remoteControl.pressButton();

        // Turning off the light
        remoteControl.setCommand(turnOffCommand);
        remoteControl.pressButton();

        // Undo last command
        remoteControl.setCommand(undoCommand);
        remoteControl.pressButton();
    }
}


// INTERPRETER PATTERN

// Abstract Expression
interface Expression {
    int interpret(Context context);
}

// Terminal Expression: NumberExpression
class NumberExpression implements Expression {
    private int number;

    public NumberExpression(int number) {
        this.number = number;
    }

    @Override
    public int interpret(Context context) {
        return number;
    }
}

// Terminal Expression: PlusExpression
class PlusExpression implements Expression {
    private Expression left;
    private Expression right;

    public PlusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) + right.interpret(context);
    }
}

// Terminal Expression: MinusExpression
class MinusExpression implements Expression {
    private Expression left;
    private Expression right;

    public MinusExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret(Context context) {
        return left.interpret(context) - right.interpret(context);
    }
}

public class Main {
    public static void main(String[] args) {
        // Build the expression tree: 1 + (2 - 3)
        Expression expression = new PlusExpression(
            new NumberExpression(1),
            new MinusExpression(
                new NumberExpression(2),
                new NumberExpression(3)
            )
        );

        // Evaluate the expression
        Context context = new Context(); // Not used in this simple example
        int result = expression.interpret(context);

        System.out.println("Expression result: " + result); // Output: 0
    }
}


// TEMPLATE PATTERN

// Abstract class defining the template method
abstract class Beverage {
    // Template method
    public final void prepareBeverage() {
        boilWater();
        brew();
        pourInCup();
		addCondiments();
    }

    // Abstract methods to be implemented by subclasses
    abstract void brew();
    abstract void addCondiments();

    // Concrete methods
    void boilWater() {
        System.out.println("Boiling water");
    }

    void pourInCup() {
        System.out.println("Pouring into cup");
    }
}

// Concrete class: Tea
class Tea extends Beverage {
    @Override
    void brew() {
        System.out.println("Steeping the tea");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding lemon");
    }
}

// Concrete class: Coffee
class Coffee extends Beverage {
    @Override
    void brew() {
        System.out.println("Dripping coffee through filter");
    }

    @Override
    void addCondiments() {
        System.out.println("Adding sugar and milk");
    }
}

public class Main {
    public static void main(String[] args) {
        Beverage tea = new Tea();
        Beverage coffee = new Coffee();

        System.out.println("Making tea...");
        tea.prepareBeverage();

        System.out.println("\nMaking coffee...");
        coffee.prepareBeverage();
    }
}


// CHAIN OF RESPONSIBILITY PATTERN

// Handler interface
abstract class SupportHandler {
    private SupportHandler nextHandler;

    public SupportHandler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Handle request method
    public abstract void handleRequest(Request request);
}

// Concrete Handlers
class Level1SupportHandler extends SupportHandler {
    @Override
    public void handleRequest(Request request) {
        if (request.getLevel() == RequestLevel.LEVEL_ONE) {
            System.out.println("Level 1 Support Handler: Handling the request.");
        } else if (getNextHandler() != null) {
            getNextHandler().handleRequest(request);
        } else {
            System.out.println("Level 1 Support Handler: Unable to handle the request.");
        }
    }
}

class Level2SupportHandler extends SupportHandler {
    @Override
    public void handleRequest(Request request) {
        if (request.getLevel() == RequestLevel.LEVEL_TWO) {
            System.out.println("Level 2 Support Handler: Handling the request.");
        } else if (getNextHandler() != null) {
            getNextHandler().handleRequest(request);
        } else {
            System.out.println("Level 2 Support Handler: Unable to handle the request.");
        }
    }
}

class Level3SupportHandler extends SupportHandler {
    @Override
    public void handleRequest(Request request) {
        if (request.getLevel() == RequestLevel.LEVEL_THREE) {
            System.out.println("Level 3 Support Handler: Handling the request.");
        } else if (getNextHandler() != null) {
            getNextHandler().handleRequest(request);
        } else {
            System.out.println("Level 3 Support Handler: Unable to handle the request.");
        }
    }
}

// Request class
class Request {
    private RequestLevel level;

    public Request(RequestLevel level) {
        this.level = level;
    }

    public RequestLevel getLevel() {
        return level;
    }
}

// Enum for request levels
enum RequestLevel {
    LEVEL_ONE,
    LEVEL_TWO,
    LEVEL_THREE
}

public class Main {
    public static void main(String[] args) {
        // Create handlers
        SupportHandler level1Handler = new Level1SupportHandler();
        SupportHandler level2Handler = new Level2SupportHandler();
        SupportHandler level3Handler = new Level3SupportHandler();

        // Set the chain of responsibility
        level1Handler.setNextHandler(level2Handler);
        level2Handler.setNextHandler(level3Handler);

        // Process requests
        Request request1 = new Request(RequestLevel.LEVEL_TWO);
        Request request2 = new Request(RequestLevel.LEVEL_ONE);
        Request request3 = new Request(RequestLevel.LEVEL_THREE);

        level1Handler.handleRequest(request1);
        level1Handler.handleRequest(request2);
        level1Handler.handleRequest(request3);
    }
}


// STRATEGY PATTERN

// Strategy interface
interface PaymentStrategy {
    void pay(int amount);
}

// Concrete Strategies
class CreditCardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CreditCardPaymentStrategy(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid with Credit Card");
        // Additional logic for processing credit card payment
    }
}

class PayPalPaymentStrategy implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPaymentStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid with PayPal");
        // Additional logic for processing PayPal payment
    }
}

class GooglePayPaymentStrategy implements PaymentStrategy {
    private String googlePayId;

    public GooglePayPaymentStrategy(String googlePayId) {
        this.googlePayId = googlePayId;
    }

    @Override
    public void pay(int amount) {
        System.out.println(amount + " paid with Google Pay");
        // Additional logic for processing Google Pay payment
    }
}

// Context
class Payment {
    private PaymentStrategy paymentStrategy;

    public Payment(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void makePayment(int amount) {
        paymentStrategy.pay(amount);
    }
}

public class Main {
    public static void main(String[] args) {
        // Create payment instances with different strategies
        Payment creditCardPayment = new Payment(new CreditCardPaymentStrategy("1234567890123456", "12/23", "789"));
        Payment payPalPayment = new Payment(new PayPalPaymentStrategy("example@example.com", "password"));
        Payment googlePayPayment = new Payment(new GooglePayPaymentStrategy("example@googlepay"));

        // Make payments using different strategies
        creditCardPayment.makePayment(100);
        payPalPayment.makePayment(50);
        googlePayPayment.makePayment(75);

        // Changing payment strategy dynamically
        creditCardPayment.setPaymentStrategy(new CreditCardPaymentStrategy("6543210987654321", "11/24", "456"));
        creditCardPayment.makePayment(200);
    }
}


// OBSERVER PATTERN

// Observer interface
interface Observer {
    void update(int temperature, int humidity, int pressure);
}

// Subject (Observable)
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// Concrete Subject (WeatherData)
class WeatherData implements Subject {
    private List<Observer> observers;
    private int temperature;
    private int humidity;
    private int pressure;

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setMeasurements(int temperature, int humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    private void measurementsChanged() {
        notifyObservers();
    }
}

// Concrete Observers (Display Devices)
class CurrentConditionsDisplay implements Observer {
    private int temperature;
    private int humidity;

    @Override
    public void update(int temperature, int humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("Current conditions: " + temperature +
                           "F degrees and " + humidity + "% humidity");
    }
}

class StatisticsDisplay implements Observer {
    private int temperature;
    private int humidity;

    @Override
    public void update(int temperature, int humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("Statistics: " + temperature +
                           "F degrees and " + humidity + "% humidity");
    }
}

class ForecastDisplay implements Observer {
    private int temperature;
    private int humidity;

    @Override
    public void update(int temperature, int humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("Forecast: " + temperature +
                           "F degrees and " + humidity + "% humidity");
    }
}

public class Main {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();

        weatherData.registerObserver(currentDisplay);
        weatherData.registerObserver(statisticsDisplay);
        weatherData.registerObserver(forecastDisplay);

        // Simulate weather changes
        weatherData.setMeasurements(80, 65, 30);
        weatherData.setMeasurements(82, 70, 29);
        weatherData.setMeasurements(78, 90, 29);

        // Unregister an observer
        weatherData.removeObserver(forecastDisplay);

        // Simulate more weather changes
        weatherData.setMeasurements(75, 82, 28);
    }
}


// STATE PATTERN

// State interface
interface TCPState {
    void open();
    void close();
    void acknowledge();
}

// Concrete State: Closed State
class TCPClosed implements TCPState {
    @Override
    public void open() {
        System.out.println("Opening connection");
        // Transition to Established state
    }

    @Override
    public void close() {
        System.out.println("Connection is already closed");
    }

    @Override
    public void acknowledge() {
        System.out.println("Cannot acknowledge. Connection is closed.");
    }
}

// Concrete State: Established State
class TCPEstablished implements TCPState {
    @Override
    public void open() {
        System.out.println("Connection is already established");
    }

    @Override
    public void close() {
        System.out.println("Closing connection");
        // Transition to Closed state
    }

    @Override
    public void acknowledge() {
        System.out.println("Acknowledgment sent");
    }
}

// Context
class TCPConnection {
    private TCPState currentState;

    public TCPConnection() {
        currentState = new TCPClosed(); // Initial state is Closed
    }

    public void setState(TCPState state) {
        this.currentState = state;
    }

    public void open() {
        currentState.open();
        // Instead of checking with instanceof to update the state, 
        // we can pass TCPConnection object to the methods open(), close(), acknowledge(), 
   		// and update the state in those methods. 
        if (currentState instanceof TCPClosed) {
            setState(new TCPEstablished());
        }
    }

    public void close() {
        currentState.close();
        if (currentState instanceof TCPEstablished) {
            setState(new TCPClosed());
        }
    }

    public void acknowledge() {
        currentState.acknowledge();
    }
}


// PROTOTYPE PATTERN

// Prototype: Shape interface
interface Shape extends Cloneable {
    void draw();
    Shape clone();
}

// Concrete Prototype: Rectangle
class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // getters and setters

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle with width: " + width + " and height: " + height);
    }

    @Override
    public Rectangle clone() {
        try {
            return (Rectangle) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
    	Rectangle originalRectangle = new Rectangle(10, 20);
   		Rectangle clonedRectangle = originalRectangle.clone();
   		clonedRectangle.draw();
    }
}


// SINGLETON PATTERN 

// Singleton Logger class
public class Logger {
    // Private static instance of the logger
    private static volatile Logger instance;

    // Private constructor to prevent instantiation from other classes
    private Logger() {
        // Optional: Initialization code
    }

    // Public static method to get the singleton instance
    public static Logger getInstance() {
        if (instance == null) {
        	synchronized (Logger.class) {
        		if (instance == null) {
            		instance = new Logger(); 
        		}
        	}
        }
        return instance;
    }

    // Public method to log messages
    public void log(String message) {
        System.out.println(message);
    }
}

public class Main {
    public static void main(String[] args) {
        // Get the singleton instance of Logger
        Logger logger = Logger.getInstance();

        // Use the logger to log messages
        logger.log("This is a log message.");
        logger.log("Another log message.");

        // Trying to create a new instance will return the same instance
        Logger logger2 = Logger.getInstance();
        System.out.println(logger == logger2); // Output: true
    }
}


// FACTORY PATTERN

// Shape interface
interface Shape {
    void draw();
}

// Concrete Shapes: Rectangle, Circle, Square
class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}

// Shape Factory
class ShapeFactory {
    // Factory method to get shape object by type
    public Shape getShape(String shapeType) {
        if (shapeType == null) {
            return null;
        }
        if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        } else if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("SQUARE")) {
            return new Square();
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        // Get an object of Circle and call its draw method
        Shape shape1 = shapeFactory.getShape("CIRCLE");
        shape1.draw();

        // Get an object of Rectangle and call its draw method
        Shape shape2 = shapeFactory.getShape("RECTANGLE");
        shape2.draw();

        // Get an object of Square and call its draw method
        Shape shape3 = shapeFactory.getShape("SQUARE");
        shape3.draw();
    }
}


// ABSTRACT FACTORY PATTERN

// Abstract Factory: GUI Factory interface
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete Factories: Windows Factory and macOS Factory
class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacOSFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}

// Abstract Products: Button and Checkbox interfaces
interface Button {
    void paint();
}

interface Checkbox {
    void paint();
}

// Concrete Products: Windows Button, Windows Checkbox, MacOS Button, MacOS Checkbox
class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in Windows style.");
    }
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering a checkbox in Windows style.");
    }
}

class MacOSButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in macOS style.");
    }
}

class MacOSCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering a checkbox in macOS style.");
    }
}

// Client class that uses the Abstract Factory
public class Main {
    private static GUIFactory guiFactory;

    public static void main(String[] args) {
        String os = "Windows"; // or "MacOS"

        if (os.equalsIgnoreCase("Windows")) {
            guiFactory = new WindowsFactory();
        } else if (os.equalsIgnoreCase("MacOS")) {
            guiFactory = new MacOSFactory();
        } else {
            throw new UnsupportedOperationException("Unsupported operating system");
        }

        Button button = guiFactory.createButton();
        Checkbox checkbox = guiFactory.createCheckbox();

        button.paint();
        checkbox.paint();
    }
}


// BUILDER PATTERN

// Product class: User
public class User {
    private final String firstName; // required
    private final String lastName; // required
    private final int age; // optional
    private final String phone; // optional
    private final String address; // optional

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
        this.address = builder.address;
    }

    // Getters for all fields
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // Builder class
    public static class UserBuilder {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;
        private String address;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }

        // Method that constructs the final object
        public User build() {
            return new User(this);
        }
    }
}

// Main class to demonstrate the Builder pattern
public class Main {
    public static void main(String[] args) {
        User user1 = new User.UserBuilder("John", "Doe")
                .age(30)
                .phone("1234567890")
                .address("123 Main St, New York, NY")
                .build();

        User user2 = new User.UserBuilder("Jane", "Smith")
                .age(25)
                .phone("9876543210")
                .build();

        // Display user1
        System.out.println("User 1:");
        System.out.println("First Name: " + user1.getFirstName());
        System.out.println("Last Name: " + user1.getLastName());
        System.out.println("Age: " + user1.getAge());
        System.out.println("Phone: " + user1.getPhone());
        System.out.println("Address: " + user1.getAddress());

        // Display user2
        System.out.println("\nUser 2:");
        System.out.println("First Name: " + user2.getFirstName());
        System.out.println("Last Name: " + user2.getLastName());
        System.out.println("Age: " + user2.getAge());
        System.out.println("Phone: " + user2.getPhone());
        System.out.println("Address: " + user2.getAddress());
    }
}


// DECORATOR PATTERN

// Component interface: Coffee
public interface Coffee {
    double getCost(); // Returns the cost of the coffee
    String getIngredients(); // Returns the ingredients of the coffee
}

// Concrete Component: SimpleCoffee
class SimpleCoffee implements Coffee {
    @Override
    public double getCost() {
        return 1.0; // Base cost of simple coffee
    }

    @Override
    public String getIngredients() {
        return "Coffee"; // Ingredients of simple coffee
    }
}

// Decorator: CoffeeDecorator
abstract class CoffeeDecorator implements Coffee {
    protected final Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee decoratedCoffee) {
        this.decoratedCoffee = decoratedCoffee;
    }

    public double getCost() {
        return decoratedCoffee.getCost();
    }

    public String getIngredients() {
        return decoratedCoffee.getIngredients();
    }
}

// Concrete Decorators: Milk, Sugar
class Milk extends CoffeeDecorator {
    public Milk(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    public double getCost() {
        return super.getCost() + 0.5; // Additional cost of milk
    }

    public String getIngredients() {
        return super.getIngredients() + ", Milk"; // Additional ingredient
    }
}

class Sugar extends CoffeeDecorator {
    public Sugar(Coffee decoratedCoffee) {
        super(decoratedCoffee);
    }

    public double getCost() {
        return super.getCost() + 0.2; // Additional cost of sugar
    }

    public String getIngredients() {
        return super.getIngredients() + ", Sugar"; // Additional ingredient
    }
}

// Main class to demonstrate the Decorator pattern
public class Main {
    public static void main(String[] args) {
        // Ordering a simple coffee
        Coffee coffee = new SimpleCoffee();
        System.out.println("Cost: $" + coffee.getCost() + ", Ingredients: " + coffee.getIngredients());

        // Adding milk to the coffee
        coffee = new Milk(coffee);
        System.out.println("Cost: $" + coffee.getCost() + ", Ingredients: " + coffee.getIngredients());

        // Adding sugar to the coffee
        coffee = new Sugar(coffee);
        System.out.println("Cost: $" + coffee.getCost() + ", Ingredients: " + coffee.getIngredients());
    }
}


// PROXY PATTERN

// Subject interface: Image
interface Image {
    void display();
}

// Real Subject: RealImage
class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading " + filename);
    }

    public void display() {
        System.out.println("Displaying " + filename);
    }
}

// Proxy: ProxyImage
class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}

// Main class to demonstrate the Proxy pattern
public class Main {
    public static void main(String[] args) {
        Image image1 = new ProxyImage("test_10mb.jpg");
        Image image2 = new ProxyImage("test_20mb.jpg");

        // Image will be loaded from disk
        image1.display();
        // Image will not be loaded from disk
        image1.display();
        // Image2 will be loaded from disk
        image2.display();
        // Image2 will not be loaded from disk
        image2.display();
    }
}


// COMPOSITE PATTERN

// Component
public interface FileSystemComponent {
    void showDetails();
}

// Leaf
public class File implements FileSystemComponent {
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void showDetails() {
        System.out.println("File: " + name + " (Size: " + size + "KB)");
    }
}

// Intermediate
public class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void addComponent(FileSystemComponent component) {
        components.add(component);
    }

    public void removeComponent(FileSystemComponent component) {
        components.remove(component);
    }

    @Override
    public void showDetails() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component : components) {
            component.showDetails();
        }
    }
}

public class CompositePatternDemo {
    public static void main(String[] args) {
        FileSystemComponent file1 = new File("File1.txt", 10);
        FileSystemComponent file2 = new File("File2.txt", 20);
        FileSystemComponent file3 = new File("File3.txt", 30);

        Directory dir1 = new Directory("Dir1");
        Directory dir2 = new Directory("Dir2");

        dir1.addComponent(file1);
        dir1.addComponent(file2);

        dir2.addComponent(file3);
        dir2.addComponent(dir1);

        dir2.showDetails();
    }
}


// ADAPTER PATTERN

// Target interface
public interface WeightCalculator {
    double calculateWeightInKgs();
}

// Adaptee
public class WeightCalculatorInPounds {
    private double weightInPounds;

    public WeightCalculatorInPounds(double weightInPounds) {
        this.weightInPounds = weightInPounds;
    }

    public double getWeightInPounds() {
        return weightInPounds;
    }
}

// Adapter
public class WeightCalculatorAdapter implements WeightCalculator {
    private WeightCalculatorInPounds weightCalculatorInPounds;

    public WeightCalculatorAdapter(WeightCalculatorInPounds weightCalculatorInPounds) {
        this.weightCalculatorInPounds = weightCalculatorInPounds;
    }

    @Override
    public double calculateWeightInKgs() {
        // Conversion from pounds to kilograms
        return weightCalculatorInPounds.getWeightInPounds() * 0.453592;
    }
}

public class AdapterPatternDemo {
    public static void main(String[] args) {
        WeightCalculatorInPounds weightInPounds = new WeightCalculatorInPounds(100);
        WeightCalculator adapter = new WeightCalculatorAdapter(weightInPounds);

        double weightInKgs = adapter.calculateWeightInKgs();
        System.out.println("Weight in Kgs: " + weightInKgs);
    }
}


// BRIDGE PATTERN

// Implementor
public interface MessageSender {
    void sendMessage(String message);
}


// Concrete Implementor for SMS
public class SmsSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

// Concrete Implementor for Email
public class EmailSender implements MessageSender {
    @Override
    public void sendMessage(String message) {
        System.out.println("Sending Email: " + message);
    }
}


// Abstraction
public abstract class Notification {
    protected MessageSender messageSender;

    public Notification(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public abstract void send(String message);
}

// Refined Abstraction for Alert Notification
public class AlertNotification extends Notification {

    public AlertNotification(MessageSender messageSender) {
        super(messageSender);
    }

    @Override
    public void send(String message) {
        System.out.print("Alert Notification: ");
        messageSender.sendMessage(message);
    }
}

// Refined Abstraction for Reminder Notification
public class ReminderNotification extends Notification {

    public ReminderNotification(MessageSender messageSender) {
        super(messageSender);
    }

    @Override
    public void send(String message) {
        System.out.print("Reminder Notification: ");
        messageSender.sendMessage(message);
    }
}

public class BridgePatternDemo {
    public static void main(String[] args) {
        MessageSender smsSender = new SmsSender();
        MessageSender emailSender = new EmailSender();

        Notification alertNotification = new AlertNotification(smsSender);
        Notification reminderNotification = new ReminderNotification(emailSender);

        alertNotification.send("This is an alert!");
        reminderNotification.send("This is a reminder!");
    }
}


// FACADE PATTERN

// Subsystem classes: AudioPlayer and VideoPlayer
class AudioPlayer {
    public void playAudio(String fileName) {
        System.out.println("Playing audio file: " + fileName);
    }
}

class VideoPlayer {
    public void playVideo(String fileName) {
        System.out.println("Playing video file: " + fileName);
    }
}

// Facade class: MultimediaFacade
class MultimediaFacade {
    private AudioPlayer audioPlayer;
    private VideoPlayer videoPlayer;

    public MultimediaFacade() {
        audioPlayer = new AudioPlayer();
        videoPlayer = new VideoPlayer();
    }

    public void playAudio(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            audioPlayer.playAudio(fileName);
        } else {
            System.out.println("Unsupported audio format");
        }
    }

    public void playVideo(String videoType, String fileName) {
        if (videoType.equalsIgnoreCase("mp4")) {
            videoPlayer.playVideo(fileName);
        } else {
            System.out.println("Unsupported video format");
        }
    }
}

// Main class to demonstrate the Facade pattern
public class Main {
    public static void main(String[] args) {
        MultimediaFacade multimediaFacade = new MultimediaFacade();

        // Playing audio
        multimediaFacade.playAudio("mp3", "song.mp3");

        // Playing video
        multimediaFacade.playVideo("mp4", "movie.mp4");

        // Trying to play unsupported formats
        multimediaFacade.playAudio("wav", "music.wav");
        multimediaFacade.playVideo("avi", "video.avi");
    }
}


// FLYWEIGHT PATTERN

// Flyweight interface: TextCharacter
interface TextCharacter {
    void printCharacter(String fontName);
}

// Concrete Flyweight: Character
class Character implements TextCharacter {
    private char character; // Intrinsic state

    public Character(char character) {
        this.character = character;
    }

    @Override
    public void printCharacter(String fontName) {
        System.out.println("Character " + character + " printed in " + fontName + " font");
    }
}

// Flyweight Factory: CharacterFactory
class CharacterFactory {
    private Map<Character, Character> characters = new HashMap<>();

    public TextCharacter getCharacter(char character) {
        if (!characters.containsKey(character)) {
            characters.put(character, new Character(character));
        }
        return characters.get(character);
    }
}

// Main class to demonstrate the Flyweight pattern
public class Main {
    public static void main(String[] args) {
        CharacterFactory factory = new CharacterFactory();

        // Print characters using the factory
        TextCharacter charA = factory.getCharacter('A');
        charA.printCharacter("Arial");

        TextCharacter charB = factory.getCharacter('B');
        charB.printCharacter("Times New Roman");

        TextCharacter charC = factory.getCharacter('A');
        charC.printCharacter("Calibri");

        // charC uses the same object as charA, as 'A' has been cached
        System.out.println("charC == charA? " + (charC == charA));
    }
}


// OBJECT POOL PATTERN

// Poolable Object: DatabaseConnection
class DatabaseConnection {
    private String id;

    public DatabaseConnection(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void connect() {
        System.out.println("Connecting to database with connection: " + id);
    }

    public void disconnect() {
        System.out.println("Disconnecting from database with connection: " + id);
    }
}

// Object Pool: DatabaseConnectionPool
class DatabaseConnectionPool {
    private List<DatabaseConnection> availableConnections = new ArrayList<>();
    private List<DatabaseConnection> usedConnections = new ArrayList<>();
    private static final int MAX_CONNECTIONS = 10;

    public DatabaseConnectionPool() {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            availableConnections.add(new DatabaseConnection("Connection-" + (i + 1)));
        }
    }

    public synchronized DatabaseConnection getConnection() {
        if (availableConnections.isEmpty()) {
            System.out.println("No available connections. Please wait.");
            return null;
        }
        DatabaseConnection connection = availableConnections.remove(availableConnections.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public synchronized void releaseConnection(DatabaseConnection connection) {
        usedConnections.remove(connection);
        availableConnections.add(connection);
    }
}

// Main class to demonstrate the Object Pool pattern
public class Main {
    public static void main(String[] args) {
        DatabaseConnectionPool pool = new DatabaseConnectionPool();

        // Get connections from the pool
        DatabaseConnection connection1 = pool.getConnection();
        if (connection1 != null) {
            connection1.connect();
        }

        DatabaseConnection connection2 = pool.getConnection();
        if (connection2 != null) {
            connection2.connect();
        }

        // Release connections back to the pool
        pool.releaseConnection(connection1);
        pool.releaseConnection(connection2);

        // Get another connection from the pool
        DatabaseConnection connection3 = pool.getConnection();
        if (connection3 != null) {
            connection3.connect();
        }

        // Disconnect and release the connection
        connection3.disconnect();
        pool.releaseConnection(connection3);
    }
}


/*

SOLID Principles: 

Single Responsibility Principle (SRP): A class should have only one reason to change, meaning it should have only one job or responsibility.
Open/Closed Principle (OCP): Software entities should be open for extension but closed for modification.
Liskov Substitution Principle (LSP): Objects of a superclass should be replaceable with objects of a subclass without affecting the correctness of the program.
Interface Segregation Principle (ISP): No client should be forced to depend on methods it does not use.
Dependency Inversion Principle (DIP): High-level modules should not depend on low-level modules; both should depend on abstractions.


Creational Patterns: Design patterns that deal with object creation mechanisms, aiming to create objects in a manner suitable to the situation.

    Abstract Factory: Provides an interface for creating families of related or dependent objects without specifying their concrete classes.
    Builder: Separates the construction of a complex object from its representation, allowing the same construction process to create different representations.
    Factory Method: Defines an interface for creating an object but lets subclasses alter the type of objects that will be created.
    Prototype: Specifies the kinds of objects to create using a prototypical instance, and creates new objects by copying this prototype.
    Singleton: Ensures a class has only one instance and provides a global point of access to it.
    Object Pool: Manages a pool of reusable objects, minimizing the cost of creating and destroying objects.

Structural Patterns: Design patterns that deal with object composition, simplifying the design by identifying simple ways to realize relationships among entities.

    Adapter: Converts the interface of a class into another interface that clients expect, allowing classes to work together that couldn’t otherwise because of incompatible interfaces.
    Bridge: Decouples an abstraction from its implementation, allowing the two to vary independently.
    Composite: Composes objects into tree structures to represent part-whole hierarchies, letting clients treat individual objects and compositions uniformly.
    Decorator: Attaches additional responsibilities to an object dynamically, providing a flexible alternative to subclassing for extending functionality.
    Facade: Provides a unified interface to a set of interfaces in a subsystem, making the subsystem easier to use.
    Flyweight: Reduces the cost of creating and manipulating a large number of similar objects by sharing as much data as possible.
    Proxy: Provides a surrogate or placeholder for another object to control access to it.

Behavioral Patterns: Design patterns that deal with object interaction and responsibility, focusing on how objects communicate and interact to achieve tasks.

    Chain of Responsibility: Lets multiple objects have a chance to handle a request by passing the request along a chain of handlers.
    Command: Encapsulates a request as an object, allowing parameterization of clients with different requests, queuing of requests, and logging of requests.
    Interpreter: Defines a grammar for a language and provides an interpreter to interpret sentences in the language.
    Iterator: Provides a way to access elements of a collection sequentially without exposing its underlying representation.
    Mediator: Defines an object that encapsulates how a set of objects interact, promoting loose coupling by preventing objects from referring to each other explicitly.
    Memento: Captures and externalizes an object's internal state without violating encapsulation, allowing the object to be restored to this state later.
    Observer: Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
    State: Allows an object to alter its behavior when its internal state changes, appearing as if the object changed its class.
    Strategy: Defines a family of algorithms, encapsulates each one, and makes them interchangeable, allowing the algorithm to vary independently from clients that use it.
    Template Method: Defines the skeleton of an algorithm in a method, deferring some steps to subclasses without changing the algorithm's structure.
    Visitor: Represents an operation to be performed on elements of an object structure, allowing new operations to be defined without changing the classes of the elements on which it operates.

*/
